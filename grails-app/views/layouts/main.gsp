<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <title><g:layoutTitle default="Libra"/></title>
  <link rel="stylesheet" href="http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css" type="text/css">
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
  <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
  <g:layoutHead/>
  <g:javascript library="application"/>
</head>
<body>
<div id="doc3" class="yui-t7">
  <div id="hd" role="banner">
    <div id="spinner" class="spinner" style="display:none;">
      <img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="${message(code: 'spinner.alt', default: 'Loading...')}"/>
    </div>
    <div id="logo">
      <p><a href="${grailsApplication.config.serverURL}">LIBRA</a></p>
    </div>
  </div>
  <div id="bd" role="application">
    <div id="menu" class="yui-ge">
      <div id="navigation" role="navigation" class="yui-u first">
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
      <div id="search" role="search" class="yui-u">
        <!-- Maybe move this code out someplace else -->
        <g:form action="search" controller="search">
          <g:textField name="query"/> <g:submitButton name="search" value="Search"/>
        </g:form>
        <!-- Maybe move this code out someplace else -->
      </div>
    </div>
    <div id="main" role="main" class="yui-g">
      <g:layoutBody/>
    </div>
  </div>
  <div id="ft" role="contentinfo"><p>Copyright etc.etc. 2010-*</p></div>
</div>
</body>
</html>
