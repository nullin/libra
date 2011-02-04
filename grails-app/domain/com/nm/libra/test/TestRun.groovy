package com.nm.libra.test

class TestRun {
    String name
    Date dateCreated
    static constraints = {
        name(unique:true)
    }
    static mapping = {
        sort "name"
    }
    def static getLatestRunName() {
        def testRunList = list(sort:'dateCreated', order:'desc')
        return testRunList.size() == 0 ? null : testRunList[0]
    }
}
