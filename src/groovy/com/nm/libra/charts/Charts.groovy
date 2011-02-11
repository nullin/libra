package com.nm.libra.charts

import java.awt.Color
import org.jfree.chart.ChartUtilities
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot3D
import org.jfree.data.general.DefaultPieDataset
import org.jfree.util.Rotation

/**
 * Helper class to generate charts
 * @author nullin
 */
class Charts {

  def static generatePieChart(keys, values) {
    System.out.println "Chart#generatePieChart : " + keys + " " + values
    if (!keys || !values) {
      System.err.println  "No data specified to generate pie chart"
      return
    }

    if (keys.size() != values.size()) {
      System.err.println "Keys and values dont match w.r.t list size"
      return
    }

    //create a chart
    //log.info "Creating the chart using plot "
    DefaultPieDataset pieDataset = new DefaultPieDataset()
    PiePlot3D piePlot = new PiePlot3D(pieDataset)

    int j = 0 //in case some values are 0 and are skipped
    for (i in 0..(keys.size()-1)) {
      int value = values[i] as int
      if (value == 0) {
        continue
      }
      pieDataset.setValue(keys[i], value)
      Color color = null;
      if (keys[i] == "Pass") {
        color = Color.green
      } else if (keys[i] == "Fail") {
        color = Color.red
      } else if (keys[i] == "Skip") {
        color = Color.yellow
      } else if (keys[i] == "Unknown") {
        color = Color.black
      }
      if (color) {
        piePlot.setSectionPaint(pieDataset.getKey(j++), color)
      }
    }

    piePlot.startAngle = 290;
    piePlot.direction = Rotation.CLOCKWISE
    piePlot.foregroundAlpha = 0.5f
    piePlot.backgroundAlpha = 1.0f
    piePlot.backgroundPaint = null
    piePlot.noDataMessage = "No data to display"
    piePlot.circular = true
    piePlot.outlineVisible = false

    JFreeChart chart = new JFreeChart(piePlot)
    chart.removeLegend()
    chart.borderVisible = false
    chart.backgroundImageAlpha = 1.0f
    chart.backgroundPaint = null

    try
    {
       return ChartUtilities.encodeAsPNG(chart.createBufferedImage(250, 250, null))
       System.out.println "[JFreeChart done]"
    } catch (java.io.IOException exc) {
       System.err.println  "Error writing image to file"
    }
  }
}
