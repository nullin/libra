package com.nm.libra.test

class TestRunCommand {
  def allTestList = []
  def passedTestList = []
  def failedTestList = []
  def skippedTestList = []
  def allConfigList = []
  def passedConfigList = []
  def skippedConfigList = []
  def failedConfigList = []

  TestRunCommand(List<TestResult> testsList) {
    this.allTestList = allTestList

    for (testResult in testsList) {
      if (testResult.testMethod.isTest) {
        if (testResult.status == TestResult.FAIL) {
          failedTestList.add(testResult)
        } else if (testResult.status == TestResult.SKIP) {
          skippedTestList.add(testResult)
        } else if (testResult.status == TestResult.PASS) {
          passedTestList.add(testResult)
        }
        allTestList.add(testResult)
      } else {
        if (testResult.status == TestResult.FAIL) {
          failedConfigList.add(testResult)
        } else if (testResult.status == TestResult.SKIP) {
          skippedConfigList.add(testResult)
        } else if (testResult.status == TestResult.PASS) {
          passedConfigList.add(testResult)
        }
        allConfigList.add(testResult)
      }
    }
  }

  def getResultMap() {
    return ['Pass': passedTestList.size(),
              'Fail': failedTestList.size(), 'Skip': skippedTestList.size(),
    'Unknown': allTestList.size() - passedTestList.size() - failedTestList.size() - skippedTestList.size()]
  }
}

class TestRunController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }

  def current = {
    //TODO: handle no test run in system
    redirect(action: "show", params: [id: TestRun.getLatestRun().id])
  }

  def list = {
    //sets max to 100, regardless of user input
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [testRunInstanceList: TestRun.list(params), testRunInstanceTotal: TestRun.count()]
  }

  def create = {
    def testRunInstance = new TestRun()
    testRunInstance.properties = params
    return [testRunInstance: testRunInstance]
  }

  def save = {
    def testRunInstance = new TestRun(params)
    if (testRunInstance.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'testRun.label', default: 'TestRun'), testRunInstance.id])}"
      redirect(action: "show", id: testRunInstance.id)
    }
    else {
      render(view: "create", model: [testRunInstance: testRunInstance])
    }
  }

  def qir = {

  }

  def show = {
    def testRunInstance = TestRun.get(params.id)
    if (!testRunInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testRun.label', default: 'TestRun'), params.id])}"
      redirect(action: "list")
    }
    else {
      //TODO: optimize

      TestRunCommand trCmd = new TestRunCommand(TestResult.findAllByTestRun(testRunInstance))

      [testRunInstance: testRunInstance, testRunCmd:trCmd]
    }
  }

  def edit = {
    def testRunInstance = TestRun.get(params.id)
    if (!testRunInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testRun.label', default: 'TestRun'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [testRunInstance: testRunInstance]
    }
  }

  def update = {
    def testRunInstance = TestRun.get(params.id)
    if (testRunInstance) {
      if (params.version) {
        def version = params.version.toLong()
        if (testRunInstance.version > version) {

          testRunInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'testRun.label', default: 'TestRun')] as Object[], "Another user has updated this TestRun while you were editing")
          render(view: "edit", model: [testRunInstance: testRunInstance])
          return
        }
      }
      testRunInstance.properties = params
      if (!testRunInstance.hasErrors() && testRunInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'testRun.label', default: 'TestRun'), testRunInstance.id])}"
        redirect(action: "show", id: testRunInstance.id)
      }
      else {
        render(view: "edit", model: [testRunInstance: testRunInstance])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testRun.label', default: 'TestRun'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    log.info "Delete TestRun : " + params
    def testRunInstance = TestRun.get(params.id)
    if (testRunInstance) {
      try {
        testRunInstance.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'testRun.label', default: 'TestRun'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'testRun.label', default: 'TestRun'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testRun.label', default: 'TestRun'), params.id])}"
      redirect(action: "list")
    }
  }
}
