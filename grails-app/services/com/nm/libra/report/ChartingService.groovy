package com.nm.libra.report

import com.nm.libra.charts.Charts

class ChartingService {

  static transactional = true

  def renderChart(paramsMap) {
    if (paramsMap?.type == "pie") {
      return Charts.generatePieChart(paramsMap.keys, paramsMap.values)
    }
    return null
  }
}
