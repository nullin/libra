
<%@ page import="com.nm.libra.test.TestMethod" %>
<%@ page import="com.nm.libra.test.TestMethodService" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Search TestMethods</title>
        <meta name="layout" content="main" />
        <g:javascript library="prototype" />
    </head>
    <body>
       <h1>Search for test methods</h1>
       <trl:flashMessage message="${flash.message}" />
       <g:form>
           <table>
               <tr><td>Package/Class/Method Name contains:</td>
               <td><g:textField name="testname"/></td></tr>
               <tr><td>Test/Config?:</td>
               <td><g:select name="testConfig" from="${TestMethodService.testConfig}" noSelection="${['null':'Test or Config']}"/></td></tr>
               <tr><td><g:submitToRemote action="asyncSearch" value="Search" update="[success:'searchResults',failure:'error']"/></td></tr>
           </table>
       </g:form>

       <h2>Results</h2>
       <div id="error"></div>
       <div id="searchResults">
           <p>Not searched yet!</p>
       </div>
    </body>
</html>
