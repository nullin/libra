package com.nm.libra.test

class TestMethod {
    String packageName
    String className
    String methodName
    String instanceName
    static belongsTo = [suite : Suite]
    static hasMany = [testResults : TestResult]
    boolean isTest
    String name //internal

    static constraints = {
        packageName(blank:false, nullable:false) //force package names for tests for now
        className(blank:false, nullable:false)
        methodName(blank:false, nullable:false)
        //Add check to ensure suitename + test name is unique
    }

    static mapping = {
        sort "name"
    }

    String toString() {
        "TestMethod [name:'${getName()}', suite:'${suite.name}', isTest:${isTest}]"
    }

    static transients = ['displayName']

    def beforeInsert = {
        name = generateName()
    }

    def beforeUpdate = {
        name = generateName()
    }

    def generateName() {
        def name = packageName + "." + className + "." + methodName
        if (instanceName) {
            name += "@" + instanceName
        }
        return name
    }

    def getDisplayName() {
        return name.replace("@", "<br/>@")
    }
}
