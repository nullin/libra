package com.nm.libra.test

import com.nm.libra.test.TestResult;
import com.nm.libra.test.TestRun;
import com.nm.libra.utils.GoogleChartBuilderNew;

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
            def failedTestsList = []
            def skippedTestsList = []
            def failedConfigsList = []
            def skippedConfigsList = []
            def allTestsList = []
            for (testResult in TestResult.findAllByTestRun(testRunInstance)) {
                if (testResult.testMethod.isTest) {
                    if (testResult.status == TestResult.FAIL) {
                        failedTestsList.add(testResult)
                    } else if (testResult.status == TestResult.SKIP) {
                        skippedTestsList.add(testResult)
                    }
                    allTestsList.add(testResult)
                } else {
                    if (testResult.status == TestResult.FAIL) {
                        failedConfigsList.add(testResult)
                    } else if (testResult.status == TestResult.SKIP) {
                        skippedConfigsList.add(testResult)
                    }
                }
            }

            def resultsMap = ['Pass': allTestsList.size() - failedTestsList.size() - skippedTestsList.size(),
                'Fail': failedTestsList.size(), 'Skip': skippedTestsList.size() ]

            def chart = new GoogleChartBuilderNew()
            def result = chart.pie3DChart{
              size(w:400, h:200)
              data(encoding:'simple'){
                  dataSet([1,2,3])
              }
              labels{
                  label('1L')
                  label('2L')
                  //label('3L')
              }
              legend{
                  label('Pass')
                  label('Fail')
                  //label('Skip')
              }
//              colors{
//                  color('5CE65C')
//                  color('FF3300')
//                  //color('FFCC00')
//              }
            }

            [testRunInstance: testRunInstance, failedTestsList: failedTestsList,
                skippedTestsList: skippedTestsList, failedConfigsList: failedConfigsList,
                skippedConfigsList: skippedConfigsList, allTestsList: allTestsList,
                resultsMap: resultsMap, result:result]
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
