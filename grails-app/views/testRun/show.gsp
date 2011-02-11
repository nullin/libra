<%@ page import="com.nm.libra.test.TestRun" %>
<%@ page import="com.nm.libra.test.TestMethod" %>
<%@ page import="com.nm.libra.test.TestResult" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main"/>
  <title>TestRun ${testRunInstance.name}</title>
</head>
<body>
  <h1>Results for run '${testRunInstance.name}'</h1>
  <trl:flashMessage message="${flash.message}"/>
  %{--<img alt="Test Results" src="<g:createLink action="renderChart" controller="chart" params="${resultsMap}"/>">--}%
  <img alt="Test Results" src="<trl:renderChart dataMap="${testRunCmd.resultMap}" type="pie"/>">
  <p>Total: <b>${testRunCmd.allTestList.size()}</b>,&nbsp;
  Pass: <b>${testRunCmd.passedTestList.size()}</b>,&nbsp;
  Fail: <b>${testRunCmd.failedTestList.size()}</b>,&nbsp;
  Skip: <b>${testRunCmd.skippedTestList.size()}</b></p>
  <h2>Failed Tests</h2>
  <trl:displayTestResultsTable results="${testRunCmd.failedTestList}"/>
  <h2>Skipped Tests</h2>
  <trl:displayTestResultsTable results="${testRunCmd.skippedTestList}"/>
  <h2>Failed Configurations</h2>
  <trl:displayTestResultsTable results="${testRunCmd.failedConfigList}"/>
  <h2>Skipped Configurations</h2>
  <trl:displayTestResultsTable results="${testRunCmd.skippedConfigList}"/>
  <h2>All Tests</h2>
  <trl:displayTestResultsTable results="${testRunCmd.allTestList}" showStatus="true"/>
</body>
</html>
