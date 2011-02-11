package com.nm.libra.input

import com.nm.libra.test.TestRun
import groovy.io.FileType

class StoreController {

  ParseService parseService = new ParseService()
  def index = {
    //redirect(action: storeall)
  }

  def store = {
    boolean processed = false
    def result = null
    def i = 0
    while (!processed && i++ < 5) {
      log.info "Trying request ${i} : " + params.classname + "." + params.methodname + " " + params.suite
      try {
        result = parseService.store(params)
        processed = true
      } catch (Exception e) {
        //TODO: fix to be more specific exceptions like dup_violation or staleobject exception etc
        log.error "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX " + e.message
      }
      Thread.sleep 2000
    }

    response.status = 200
    render result.error ? result.error : "Added ${result.added}, Updated ${result.updated}"
  }

  def storeall = {
    String relPath = '/Users/nullin/Downloads/testng/sample/'
    parseService.parse(relPath + 'testng-results-long.xml', "run0")
    parseService.parse(relPath + 'testng-results-1.xml', "run1")
    parseService.parse(relPath + 'testng-results-2.xml', "run2")
    parseService.parse(relPath + 'testng-results-4.xml', "run4")
    render('Done with all')
  }

  def storeBig = {
    String relPath = '/Users/nullin/Downloads/testng/dump/'
    String outStr = ''
    def dumpDir = new File(relPath)
    def filterXMLFiles = ~/testng-results\.xml$/
    dumpDir.traverse(type: FileType.FILES, nameFilter: filterXMLFiles) {
      def dirName = it.parentFile.name
      log.info dirName
      parseService.parse(it.absolutePath, dirName)
    }
    render(outStr + ' Done with all')
  }

  def upload = {
    if (!params.file) {
      return []
    }
    def fileInputStream = request.getFile("file").inputStream
    def runName = params.runName ?: TestRun.getLatestRun().name
    def result = parseService.parseInputStream(fileInputStream, params.runName)
    flash.message = result.error ?
      result.error :
      "Uploaded file successfully. Added ${result.added} & updated ${result.updated} results. Go to " + g.link(action:'show', controller:"testRun", id:TestRun.findByName(runName).id) {runName}
    log.info "Uploaded file and added test results to run '${runName}'"
    return []
  }

}
