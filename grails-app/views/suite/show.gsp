<%@ page import="com.nm.libra.test.TestMethod" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main"/>
  <title>Suite ${suiteInstance.name}</title>
  <script type="text/javascript" charset="utf-8">
      $(document).ready(function() {
          $('#tm_list').dataTable();
      } );
  </script>
</head>
<body>
<h1>${suiteInstance.name}</h1>
<trl:flashMessage message="${flash.message}"/>

<h2>Test Methods</h2>
<table id="tm_list">
  <thead>
  <tr>
    <th>Test Method</th>
  </tr>
  </thead>
  <tbody>
  <g:each in="${TestMethod.findAllBySuiteAndIsTest(suiteInstance, true).sort(){it.name}}"
          status="i" var="testMethod">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
    <td><g:link action="show" controller="testMethod" id="${testMethod.id}">${testMethod.displayName}</g:link></td>
  </g:each>
  </tbody>
</table>
</body>
</html>
