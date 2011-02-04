
<%@ page import="com.nm.libra.test.TestRun" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Upload Test Results</title>
    </head>
    <body>
       <h1>Upload New Test Results</h1>
       <trl:flashMessage message="${flash.message}" />
       <p><b>WARNING:</b> Unless TestRun is specified, it will upload/overwrite results
       in latest test run.</p>
       <div>
           <g:uploadForm action="upload">
               <table>
                   <tr><td>Test Run Name: </td><td><g:select name="runName" from="${TestRun.list()}" optionValue="name" optionKey="name"
               noSelection="${['null':'Please Choose...']}" /></td></tr>
                   <tr><td>Test Run Result File: </td><td><input type="file" name="file" /></td></tr>
                   <tr><td><g:submitButton name="upload" value="Upload"/></td></tr>
               </table>
           </g:uploadForm>
       </div>
       <div>
           <!-- TODO some AJAX stuff here -->
       </div>
    </body>
</html>
