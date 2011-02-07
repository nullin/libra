
<%@ page import="com.nm.libra.test.TestRun" %>
<%@ page import="com.nm.libra.test.TestResult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'testRun.label', default: 'TestRun')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
<%--        <div class="body">--%>
            <h1>TestRuns</h1>
            <trl:flashMessage message="${flash.message}" />
            <div class="li_st">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Result Count<br/>(Test Only)</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${testRunInstanceList}" status="i" var="testRunInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${testRunInstance.id}">${fieldValue(bean: testRunInstance, field: "name")}</g:link></td>
                            <td>${TestResult.findAll("from TestResult tr where tr.testMethod.isTest = true").size()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginate_Buttons">
                <g:paginate total="${testRunInstanceTotal}" />
            </div>
<%--        </div>--%>
    </body>
</html>
