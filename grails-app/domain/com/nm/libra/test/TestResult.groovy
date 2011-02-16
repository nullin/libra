package com.nm.libra.test

/**
 * A test result for a test method
 */
class TestResult {

  static PASS = "PASS"
  static FAIL = "FAIL"
  static NOTRUN = "NOTRUN"
  static RUNNING = "RUNNING"
  static SKIP = "SKIP"
  static UNKNOWN = "UNKNOWN"
  /** list of test states  */
  static STATUS_LIST = [PASS, FAIL, NOTRUN, RUNNING, SKIP, UNKNOWN]

  /** Test run this result belongs to  */
  TestRun testRun
  /** the test method  */
  static belongsTo = [testMethod: TestMethod, testRun: TestRun]
  /** test status  */
  String status
  /** bug number, if any  */
  long bugNumber
  /** comments, if any  */
  String comments
  /** duration taken for a method to run  */
  long duration
  /** approximate start time  */
  long startTime
  /** approximate end time  */
  long endTime

  static searchable = true
  static constraints = {
    testRun(nullable: false)
    comments(nullable: true)
    //TODO: add status constraints
    //TODO: add constraint for startTime <= endTime ?
  }

  /**
   * Gets the duration taken to run this method
   * @return the method execution time
   */
  def getDuration() {
    if (startTime && endTime && !duration) {
      return endTime - startTime
    } else {
      return duration
    }
  }
}
