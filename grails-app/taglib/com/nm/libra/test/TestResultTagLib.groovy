package com.nm.libra.test

import com.nm.libra.utils.DateUtil;

class TestResultTagLib {
  static namespace = "trl"

  def displayTestResultsTable = { attrs ->
    def showStatus = attrs.showStatus ?: false
    if (attrs.results) {
      if (attrs.table_id) {
        out << "<table id=\"${attrs.table_id}\">"
      } else {
        out << "<table>"
      }
      out << "<thead><tr>"
      out << "<th>Name</th>"
      if (showStatus) {
        out << "<th>Status</th>"
      }
      out << "<th>Started At</th>"
      out << "<th>Finished At</th>"
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
          def cssClass = ""
          def testStatus = testResult.status.toUpperCase()
          if (testStatus == TestResult.PASS) {
            cssClass = "st_pass"
          } else if (testStatus == TestResult.FAIL) {
            cssClass = "st_fail"
          } else if (testStatus == TestResult.SKIP) {
            cssClass = "st_skip"
          } else {
            cssClass = "st_not_run"
          }
          out << "<td class=\"center ${cssClass}\">" + testResult.status + "</td>"
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
