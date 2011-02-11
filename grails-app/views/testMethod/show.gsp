<%@ page import="com.nm.libra.test.TestMethod" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main"/>
  <title>TestMethod ${testMethodInstance.name}</title>
</head>
<body>
<h1>${testMethodInstance.displayName}</h1>
<trl:flashMessage message="${flash.message}"/>
<p>
  Health: <b>${passRate}%</b><br/>
  Suite: <b>${testMethodInstance.suite.name}</b><br/>
  Package: <b>${testMethodInstance.packageName}</b><br/>
  Class: <b>${testMethodInstance.className}</b><br/>
  Method: <b>${testMethodInstance.methodName}</b><br/>
  <g:if test="${testMethodInstance.instanceName}">
    Instance: <b>${testMethodInstance.instanceName}</b><br/>
  </g:if>
</p>

<h2>Results</h2>
<table>
  <thead>
  <tr>
    <td>Test Run</td>
    <td>Status</td>
    <td>Duration (ms)</td>
    <td>Started At</td>
    <td>Ended At</td>
  </tr>
  </thead>
  <tbody>
  <g:each in="${testMethodInstance.testResults}" status="i" var="testResult">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
    <td><g:link action="show" controller="testRun" id="${testResult.testRun.id}">${testResult.testRun.name}</g:link></td>
    <td>${testResult.status}</td>
    <td>${testResult.duration}</td>
    <td>${new Date(testResult.startTime)}</td>
    <td>${new Date(testResult.endTime)}</td>
  </g:each>
  </tbody>
</table>
</body>
</html>
