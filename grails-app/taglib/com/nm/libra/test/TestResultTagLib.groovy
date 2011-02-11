package com.nm.libra.test

import com.nm.libra.utils.DateUtil;

class TestResultTagLib {
  static namespace = "trl"

  def displayTestResultsTable = { attrs ->
    def showStatus = attrs.showStatus ?: false
    if (attrs.results) {
      out << "<table><thead><tr>"
      out << "<td>Name</td>"
      if (showStatus) {
        out << "<td>Status</td>"
      }
      out << "<td>Started At</td>"
      out << "<td>Finished At</td>"
      out << "</tr></thead><tbody>"
      def i = 0
      for (testResult in attrs.results) {
        out << "<tr class=\"" + ((i++ % 2) == 0 ? "odd" : "even") + "\">"
        out << "<td>"
        out << g.link(action: "show", controller: "testMethod", id: "${testResult.testMethod.id}") {
          testResult.testMethod.displayName
        }
        out << "</td>"
        if (showStatus) {
          out << "<td>" + testResult.status + "</td>"
        }
        out << "<td>" + DateUtil.getISODateAsString(new Date(testResult.startTime)) + "</td>"
        out << "<td>" + DateUtil.getISODateAsString(new Date(testResult.endTime)) + "</td>"
        out << "</tr>"
      }
      out << "</tbody></table>"
    } else {
      out << "<p>None</p>"
    }
  }
}
