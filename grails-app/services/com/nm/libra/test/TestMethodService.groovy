package com.nm.libra.test


import com.nm.libra.test.TestMethod;
import com.nm.libra.test.TestResult;

class TestMethodService {

  static transactional = true
  static testConfig = ['Test', 'Config']

  def getPassRate(testInstance) {
    if (testInstance) {
      def results = testInstance.testResults?.collect {it.status}
      if (results) {
        def allResults = results.size()
        def passResults = results.findAll { it == TestResult.PASS}.size()
        return (int) ((passResults * 100) / allResults)
      }
    }
    return 0
  }

  def search(paramsMap) {
    log.info paramsMap
    def testname = paramsMap.testname

    def tmList = null
    if (paramsMap.testConfig) {
      def isTest = paramsMap.testConfig == testConfig[0] ? true : false
      tmList = TestMethod.withCriteria() {
        ilike("name", '%' + testname + '%')
        eq("isTest", isTest)
      }
    } else {
      tmList = TestMethod.withCriteria() {
        ilike("name", '%' + testname + '%')
      }
    }
    log.info "Test Method List: " + tmList
    return tmList
  }

}
