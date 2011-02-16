<%@ page import="com.nm.libra.test.TestMethod" %>
<%@ page import="com.nm.libra.test.TestMethodService" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Search Test Methods</title>
  <meta name="layout" content="main"/>
  <script type="text/javascript" charset="UTF-8">
    function handleSearch(form) {
      $("#search_results").dataTable( {
        "iDisplayLength": 25,
		"bProcessing": true,
		"bServerSide": false,
        "bDestroy": true,
		"sAjaxSource": "<g:createLink action="asyncSearch" />",
		"fnServerData": function ( sSource, aoData, fnCallback ) {
          var formData = aoData;
          if (form) {
            formData = {"testname":form.testname.value, "testConfig":form.testConfig.value};
          }
          $.getJSON( sSource, formData, function (json) {
            fnCallback(json)
          } );
		}
	  } );
    }
    //initialize datatables
    $(document).ready(function() {
      handleSearch();
    });
  </script>
</head>
<body>
<h1>Search for Test/Configuration Methods</h1>
<trl:flashMessage message="${flash.message}"/>
<g:form>
  <table>
    <tr><td>Package/Class/Method Name contains:</td>
      <td><g:textField name="testname"/></td></tr>
    <tr><td>Test/Config?:</td>
      <td><g:select name="testConfig" from="${TestMethodService.testConfig}" noSelection="${['null':'Test or Config']}"/></td></tr>
    <tr><td>
      <g:submitButton name="search" value="Search" onclick="handleSearch(this.form);return false;"/>
    </td></tr>
  </table>
</g:form>

<h2>Results</h2>
<table id="search_results">
  <thead><tr><th>Name</th><th>Suite</th></tr></thead>
  <tbody><tr><td>Not Searched yet!</td></tr></tbody>
</table>
</body>
</html>
