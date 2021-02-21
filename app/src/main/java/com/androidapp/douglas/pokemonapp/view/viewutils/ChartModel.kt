package com.androidapp.douglas.pokemonapp.view.viewutils

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle

/**
 * The Chart used to shown the pokemon stats.
 */
class ChartModel {

    /**
     * Gets the chart model with some info set.
     */
    fun getChartModel(): AAChartModel {
        return AAChartModel()
            .chartType(AAChartType.Column)
            .subtitleStyle(SUBTITLE_STYLE_COLOR)
            .backgroundColor(android.R.color.transparent)
            .axesTextColor(AXES_STYLE_COLOR)
            .dataLabelsEnabled(true)
            .dataLabelsStyle(DATA_LABEL_STYLE_COLOR)
            .xAxisVisible(false)
            .yAxisVisible(false)
            .yAxisMax(255f)
            .touchEventEnabled(false)
    }

    companion object {
        private const val WHITE_COLOR = "#FFFFFF"
        private val DATA_LABEL_STYLE_COLOR = AAStyle().color(WHITE_COLOR)
        private val SUBTITLE_STYLE_COLOR = AAStyle().color(WHITE_COLOR).fontSize(30f)
        private const val AXES_STYLE_COLOR = WHITE_COLOR
    }
}