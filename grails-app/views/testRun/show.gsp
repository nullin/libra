
<%@ page import="com.nm.libra.test.TestRun" %>
<%@ page import="com.nm.libra.test.TestMethod" %>
<%@ page import="com.nm.libra.test.TestResult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>TestRun ${testRunInstance.name}</title>
    </head>
    <body>
       <h1>Results for run '${testRunInstance.name}'</h1>
       <trl:flashMessage message="${flash.message}" />
       <img alt="Test Results" src="${result.toString()}">
       <p>Total: <b>${allTestsList.size()}</b>,&nbsp;
       Pass: <b>${allTestsList.size() - failedTestsList.size() - skippedTestsList.size()}</b>,&nbsp;
       Fail: <b>${failedTestsList.size()}</b>,&nbsp;
       Skip: <b>${skippedTestsList.size()}</b></p>
       <h2>Failed Tests</h2>
       <trl:displayTestResultsTable results="${failedTestsList}"/>
       <h2>Skipped Tests</h2>
       <trl:displayTestResultsTable results="${skippedTestsList}"/>
       <h2>Failed Configurations</h2>
       <trl:displayTestResultsTable results="${failedConfigsList}"/>
       <h2>Skipped Configurations</h2>
       <trl:displayTestResultsTable results="${skippedConfigsList}"/>
       <h2>All Tests</h2>
       <trl:displayTestResultsTable results="${allTestsList}" showStatus="true"/>
    </body>
</html>
