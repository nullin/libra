package com.nm.libra.report

class ChartController {

  def chartingService
  def index = { }

  def renderChart = {
    log.info "renderChart params : " + params
    def img = chartingService.renderChart(params)
    if (img) {
      response.setContentLength(img.length)
      response.outputStream.write(img)
    } else {
      response.sendError(404)
    }
  }
}
