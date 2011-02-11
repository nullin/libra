package com.nm.libra.input

import com.nm.libra.test.Suite
import com.nm.libra.test.TestMethod
import com.nm.libra.test.TestResult
import com.nm.libra.test.TestRun
import com.nm.libra.utils.DateUtil
import org.xml.sax.SAXException

class ParseService {

  static transactional = true
  static enum Supported_Formats {
    XML, TEXT
  }

  /**
   * Parses and returns the runID of the TestRun for which the results are stored
   *
   * Returns negative number if there is an error/exception
   *
   * @param filepath
   * @param runName
   * @return
   */
  def parse(filepath, runName, format = Supported_Formats.XML) {
    log.info "Parsing ${filepath}"
    def inputStream = null
    try {
      inputStream = new FileInputStream(filepath)
    } catch (FileNotFoundException e) {
      log.error "${filepath} is invalid", e
      return [error: "${filepath} is invalid"]
    }

    return parseInputStream(inputStream, runName, format)
  }

  def parseInputStream(inputStream, runName, format = Supported_Formats.XML) {
    switch (format) {
      case Supported_Formats.XML:
        def xml = null
        try {
          xml = new XmlSlurper().parse(inputStream)
          return parseXml(xml, runName)
        } catch (SAXException e) {
          log.error "Caught SAXException", e
          return [error: "Input is not well-formed XML"]
        }
        break
      case Supported_Formats.TEXT:
        //Do nothing for now
        return [error: "Not Supported yet"]
    }
  }

  /**
   * Parses TestNG XML and returns the Run ID
   * @param xmlDoc
   * @param runName
   * @return run ID if everything is parsed successfully, or -1
   */
  def parseXml(xmlDoc, runName) {
    log.info "Parsing XML"
    def testRun = TestRun.findByName(runName)
    if (!testRun) {
      testRun = new TestRun(name: runName)
      if (!testRun.save()) {
        log.error "Failed to save run name ${runName}"
        testRun.errors.allErrors.each { log.error it }
        return [error: "Failed to save run name ${runName}"]
      }
    }

    def fullResult = [added: 0, updated: 0]
    for (suiteNode in xmlDoc.suite) {
      def suiteName = suiteNode.@name.text()
      log.debug 'SuiteName: ' + suiteName
      def suite = Suite.findByName(suiteName)
      if (!suite) {
        suite = new Suite(name: suiteName)
        if (!suite.save()) {
          log.error "Failed to save suite ${suiteName}"
          suite.errors.allErrors.each { log.error it }
          return [error: "Failed to save suite ${suiteName}"]
        }
      }

      for (classNode in suiteNode.'**'.findAll { it.name() == "class"}) {
        for (testMethodNode in classNode."test-method") {
          def instanceName = testMethodNode."@test-instance-name".text()
          def fqClassName = classNode.@name.text()
          def methodName = testMethodNode.@name.text()

          def isTestStr = testMethodNode."@is-config"?.text()
          def is_test = isTestStr == null || isTestStr == ""
          def status = testMethodNode.@status.text()
          def startTime = DateUtil.getISODate(testMethodNode."@started-at"?.text()).time
          def endTime = DateUtil.getISODate(testMethodNode."@finished-at"?.text()).time
          def duration = testMethodNode."@duration-ms"?.text() as Integer

          def result = storeTestMethodAndResult(testRun, suite, fqClassName, methodName,
                  instanceName, is_test, status, startTime, endTime, duration)
          if (result.error) {
            return result
          } else {
            fullResult.added += result.added
            fullResult.updated += result.updated
          }
        }
      }
    }
    log.info "Parsed and added ${fullResult.added} / updated  ${fullResult.updated} Results"
    return fullResult
  }

  def store(parameters) {
    def testRun = null

    if (parameters.runid) {
      testRun = TestRun.findByName(parameters.runid)
    } else {
      testRun = TestRun.findByName(TestRun.getLatestRun().name)
    }

    if (!testRun) {
      testRun = new TestRun(name: parameters.runid)
      if (!testRun.save()) {
        log.error "Failed to save run name ${parameters.runid}"
        testRun.errors.allErrors.each { log.error it }
        return [error: "Failed to save run name ${parameters.runid}"]
      }
    }

    def suiteName = parameters.suite
    log.debug 'SuiteName: ' + suiteName

    def suite = null
    suite = Suite.findByName(suiteName)
    if (!suite) {
      suite = new Suite(name: suiteName)
      if (!suite.save()) {
        log.error "Failed to save suite ${suiteName}"
        suite.errors.allErrors.each { log.error it }
        return [error: "Failed to save suite ${suiteName}"]
      }
    }

    def instanceName = parameters.instancename
    def fqClassName = parameters.classname
    def methodName = parameters.methodname

    def isTestStr = parameters.istest
    def is_test = Boolean.valueOf(isTestStr)
    def status = parameters.status
    def startTime = parameters.start
    def endTime = parameters.end
    def duration = Long.valueOf(endTime) - Long.valueOf(startTime)

    def result = storeTestMethodAndResult(testRun, suite, fqClassName, methodName,
            instanceName, is_test, status, startTime, endTime, duration)
    log.info "Added ${result.added} and Updated ${result.updated} result(s)"
    return result
  }

  def storeTestMethodAndResult(testRun, suite, fqClassName, methodName,
                               instanceName, is_test, status, startTime, endTime, duration) {
    def packageName = fqClassName.substring(0, fqClassName.lastIndexOf("."))
    def className = fqClassName.substring(fqClassName.lastIndexOf(".") + 1)

    def testMethod = TestMethod.withCriteria() {
      eq("suite", suite)
      eq("packageName", packageName)
      eq("className", className)
      eq("methodName", methodName)
      eq("instanceName", instanceName)
    }
    assert testMethod.size() < 2 //only 0 or 1
    testMethod = testMethod[0]
    if (!testMethod) {
      //add test method to db if it doesn't exist
      testMethod = new TestMethod(packageName: packageName, className: className,
              methodName: methodName, instanceName: instanceName, isTest: is_test, suite: suite)

      if (!testMethod.save()) {
        log.error "Failed to save test method for ${testMethod.name}"
        testMethod.errors.allErrors.each { log.error it }
        return [error: "Failed to save test method for ${testMethod.name}"]
      }
    }

    def testResult = TestResult.findByTestMethodAndTestRun(testMethod, testRun)
    def addedNew = true //true for new addition, false for update
    if (!testResult) {
      //create it
      testResult = new TestResult(testRun: testRun, status: status,
              duration: duration, startTime: startTime, endTime: endTime)
      testMethod.addToTestResults(testResult)
    } else {
      // or update it
      if (testResult.status != status) {
        testResult.status = status
        testResult.duration = duration
        testResult.startTime = startTime
        testResult.endTime = endTime
        if (!testResult.save()) {
          log.error "Failed to save test result for ${testResult.testMethod.name}"
          testResult.errors.allErrors.each { log.error it }
          return [error: "Failed to save test result for ${testResult.testMethod.name}"]
        }
      }
      addedNew = false
    }
    return addedNew ? [added: 1, updated: 0] : [added: 0, updated: 1]
  }
}
