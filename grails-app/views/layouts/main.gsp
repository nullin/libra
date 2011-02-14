<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <title><g:layoutTitle default="Libra"/></title>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
  <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
  <g:layoutHead/>
  <g:javascript library="application"/>
</head>
<body>
<div class="wrapper">
  <div id="hd" role="banner">
    <div id="spinner" class="spinner" style="display:none;">
      <img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="${message(code: 'spinner.alt', default: 'Loading...')}"/>
    </div>
    <div id="logo">
      <p><a href="<g:resource absolute="true" dir="/"/>">LIBRA</a></p>
    </div>
  </div>
  <div id="bd" role="application">
    <div id="menu" class="menu">
      <div id="navigation" role="navigation" class="navigation">
        <!-- Maybe move this code out someplace else -->
        <ul>
          <li><a href="${createLink(action: 'current', controller: 'testRun')}">Current Run</a></li>
          <li><a href="${createLink(action: 'list', controller: 'testRun')}">Test Runs</a></li>
          <li><a href="${createLink(action: 'upload', controller: 'store')}">Upload</a></li>
          <li><a href="${createLink(action: 'search', controller: 'testMethod')}">Search Tests</a></li>
          <li><a href="${createLink(action: 'search', controller: 'testResult')}">Search Results</a></li>
        </ul>
        <!-- Maybe move this code out someplace else -->
      </div>
      <div id="search" role="search" class="search">
        <!-- Maybe move this code out someplace else -->
        <g:form action="search" controller="search">
          <g:textField name="query"/> <g:submitButton name="search" value="Search" class="button"/>
        </g:form>
        <!-- Maybe move this code out someplace else -->
      </div>
    </div>
    <div class="clear"></div>
    <div id="main" role="main" class="main">
      <g:layoutBody/>
    </div>
  </div>
</div>
<div id="ft" class="ancillary">
  <ul class="sidebar first"><li>
    <h3>Installed Plugins</h3>
    <ul>
      <g:set var="pluginManager"
              value="${applicationContext.getBean('pluginManager')}"></g:set>
      <g:each var="plugin" in="${pluginManager.allPlugins}">
        <li>${plugin.name} - ${plugin.version}</li>
      </g:each>
    </ul>
  </li></ul>
  <ul class="sidebar">
    <h3>Application Status</h3>
    <ul>
        <li>App version: <g:meta name="app.version"></g:meta></li>
        <li>Grails version: <g:meta name="app.grails.version"></g:meta></li>
        <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
        <li>JVM version: ${System.getProperty('java.version')}</li>
        <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
        <li>Domains: ${grailsApplication.domainClasses.size()}</li>
        <li>Services: ${grailsApplication.serviceClasses.size()}</li>
        <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
      </ul>
  </ul>
  <ul class="sidebar">
    <h3>Controllers</h3>
    <ul>
      <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
      </g:each>
    </ul>
    <h3>Random Text</h3>Something here Something here Something here Something here Something here Something here Something here Something here
  </ul>
  <div class="clear"><!-- --></div></div>
<div id="copyright" role="contentinfo"><p>Copyright etc.etc. 2010. Contact <a href="mailto:nalin.makar@gmail.com">Nalin Makar</a></p></div>
</body>
</html>
