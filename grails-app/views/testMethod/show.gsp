<%@ page import="com.nm.libra.test.TestMethod" %>
<%@ page import="com.nm.libra.test.TestResult" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main"/>
  <title>TestMethod ${testMethodInstance.name}</title>
  <script type="text/javascript" charset="utf-8">
      $(document).ready(function() {
          $('#result_list').dataTable();
      } );
  </script>
</head>
<body>
<h1>${testMethodInstance.displayName}</h1>
<trl:flashMessage message="${flash.message}"/>
<p>
  Health: <b>${passRate}%</b><br/>
  Suite: <b><g:link action="show" controller="suite" id="${testMethodInstance.suite.id}">${testMethodInstance.suite.name}</g:link></b><br/>
  Package: <b>${testMethodInstance.packageName}</b><br/>
  Class: <b>${testMethodInstance.className}</b><br/>
  Method: <b>${testMethodInstance.methodName}</b><br/>
  <g:if test="${testMethodInstance.instanceName}">
    Instance: <b>${testMethodInstance.instanceName}</b><br/>
  </g:if>
</p>

<h2>Results</h2>
<table id="result_list">
  <thead>
  <tr>
    <th>Test Run</th>
    <th>Status</th>
    <th>Duration (ms)</th>
    <th>Started At</th>
    <th>Ended At</th>
  </tr>
  </thead>
  <tbody>
  <g:each in="${TestResult.findAllByTestMethod(testMethodInstance).sort(){it.startTime}}"
          status="i" var="testResult">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
    <td><g:link action="show" controller="testRun" id="${testResult.testRun.id}">${testResult.testRun.name}</g:link></td>
    <td class="st_${testResult.status.toLowerCase()}">${testResult.status}</td>
    <td>${testResult.duration}</td>
    <td>${new Date(testResult.startTime)}</td>
    <td>${new Date(testResult.endTime)}</td>
  </g:each>
  </tbody>
</table>
</body>
</html>
