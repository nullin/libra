package com.nm.libra.input

import com.nm.libra.test.TestRun

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
        log.error "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX " + e.message
      }
      Thread.sleep 2000
    }

    response.status = 200
    render result.error ? result.error : "Added ${result.added}, Updated ${result.updated}"
  }

  def storeall = {
    parseService.parse('/Users/nullin/Downloads/testng/testng-results-long.xml', "run0")
    parseService.parse('/Users/nullin/Downloads/testng/testng-results-1.xml', "run1")
    parseService.parse('/Users/nullin/Downloads/testng/testng-results-2.xml', "run2")
    parseService.parse('/Users/nullin/Downloads/testng/testng-results-4.xml', "run4")
    render('Done with all')
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
      "Uploaded file successfully. Added ${result.added} & updated ${result.updated} results."
    log.info "Uploaded file and added test results to run '${runName}'"
    return []
  }

}
