package com.nm.libra.test

/**
 * Test run to track execution of tests and suites
 */
class TestRun {

  /** name for test run */
  String name
  /** Creation date */
  Date dateCreated

  static hasMany = [testResults : TestResult]
  static searchable = true
  static constraints = {
    name(unique: true)
  }

  static mapping = {
    sort "name"
  }

  /**
   * Gets the latest test run
   * @return the latest test run, if any, or null
   */
  def static getLatestRun() {
    def testRunList = list(sort: 'dateCreated', order: 'desc')
    return testRunList.size() == 0 ? null : testRunList[0]
  }
}
