package com.nm.libra.test

class TestResult {

    static PASS = "PASS"
    static FAIL = "FAIL"
    static NOTRUN = "NOTRUN"
    static RUNNING = "RUNNING"
    static SKIP = "SKIP"
    static UNKNOWN = "UNKNOWN"
    static STATUS_LIST = [PASS, FAIL, NOTRUN, RUNNING, SKIP, UNKNOWN]

    TestRun testRun
    static belongsTo = [ testMethod : TestMethod]
    String status
    long bugNumber
    long duration

    long startTime
    long endTime
    static constraints = {
        testRun(nullable:false)
        //status(range:0..5)
        //add constraint for startTime <= endTime ?
    }
    def getDuration() {
        if (startTime && endTime && !duration) {
            return endTime - startTime
        } else {
            return duration
        }
    }
}
