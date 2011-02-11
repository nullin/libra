package com.nm.libra.report

class ChartTagLib {

  static namespace = "trl"
  def renderChart = { attrs ->
    def dataMap = attrs.dataMap
    if (dataMap) {
      def keys = []
      def values = []
      dataMap.each { k,v ->
        keys << k
        values << v
      }
      attrs["keys"] = keys
      attrs["values"] = values
    }
    /*
    Cannot remove dataMap from attrs. if removed, stale values are sent to controller
     */
    out << g.createLink(action:"renderChart", controller:"chart", params:attrs)
  }
}
