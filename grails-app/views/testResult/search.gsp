
<%@ page import="com.nm.libra.test.TestMethod" %>
<%@ page import="com.nm.libra.test.TestResult" %>
<%@ page import="com.nm.libra.test.TestMethodService" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Search TestMethods</title>
        <g:javascript library="prototype" />
    </head>
    <body>
       <h1>Search for test methods</h1>
       <trl:flashMessage message="${flash.message}" />
       <g:form>
           <table>
               <tr><td>Name contains:</td>
               <td><g:textField name="testname"/></td></tr>
               <tr><td>Status:</td>
               <td><g:select name="status" from="${TestResult.STATUS_LIST}" noSelection="${['null':'']}"/></td></tr>
               <tr><td>Test/Config?:</td>
               <td><g:select name="testConfig" from="${TestMethodService.testConfig}" noSelection="${['null':'']}"/></td></tr>
               <tr><td><g:submitToRemote action="asyncSearch" value="Search" update="[success:'mytbody',failure:'error']"/></td></tr>
           </table>
       </g:form>

       <h2>Results</h2>
       <div id="error"></div>
       <table>
               <thead>
                   <tr>
                       <td>Name</td>
                   </tr>
               </thead>
               <tbody id="mytbody">
                    <tr><td>Not Searched Yet!</td></tr>
               </tbody>
       </table>
    </body>
</html>
