package com.nm.libra.input


class StoreController {

    def parseService
    def index = {
        //redirect(action: storeall)
    }
    def store = {
        def result = parseService.store(params)
        log.info params
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
        def runName = params.runName?:TestRun.getLatestRun().name
        def result = parseService.parseInputStream(fileInputStream, params.runName)
        flash.message = result.error ? result.error : "Uploaded file successfully. Added ${result.added} & updated ${result.updated} results."
        log.info "Uploaded file and added test results to run '${runName}'"
        return []
    }

}
