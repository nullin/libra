package com.nm.libra.test

import grails.converters.JSON

class TestMethodController {

  def testMethodService
  def show = {
    def testMethodInstance = TestMethod.get(params.id)
    if (!testMethodInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testMethod.label', default: 'TestMethod'), params.id])}"
      redirect(action: "search")
    }
    else {
      def passRate = testMethodService.getPassRate(testMethodInstance)
      [testMethodInstance: testMethodInstance, passRate: passRate]
    }
  }

  def search = {
    //flash.message = "${params}"
  }

  def asyncSearch = {
    def testMethodInstances = testMethodService.search(params)
    def outData = []
    if (testMethodInstances) {
      for (testMethodInstance in testMethodInstances) {
        outData.add(["<a href=\"" + createLink(action: "show", id: testMethodInstance.id) + "\">" + testMethodInstance.name + "</a>",
          "<a href=\"" + createLink(action: "show", id: testMethodInstance.suite.id) +
                "\">" + testMethodInstance.suite.name + "</a>"])
      }
    }
    render ([aaData: outData] as JSON)
  }
}
