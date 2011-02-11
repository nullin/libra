<%@ page import="com.nm.libra.test.TestRun" %>
<%@ page import="com.nm.libra.test.TestResult" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'testRun.label', default: 'TestRun')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<%--        <div class="body">--%>
<h1>TestRuns</h1>
<trl:flashMessage message="${flash.message}"/>
  <table>
    <thead>
    <tr>
      <th>Name</th>
      <th>Created</th>
      <th>Result Count<br/>(Test Only)</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${testRunInstanceList}" status="i" var="testRunInstance">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:link action="show" id="${testRunInstance.id}">${fieldValue(bean: testRunInstance, field: "name")}</g:link></td>
        <td>${fieldValue(bean: testRunInstance, field: "dateCreated")}</td>
        <td>${TestResult.executeQuery("Select count(*) from TestResult tr where tr.testMethod.isTest = true and tr.testRun.id = ?", [testRunInstance.id])[0]}</td>
        <td><g:link action="edit" id="${testRunInstance.id}">Edit/Delete</g:link></td>
      </tr>
    </g:each>
    </tbody>
  </table>
<div class="paginate_Buttons">
  <g:paginate total="${testRunInstanceTotal}"/>
</div>
<%--        </div>--%>
</body>
</html>
