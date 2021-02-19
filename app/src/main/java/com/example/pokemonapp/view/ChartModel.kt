package com.example.pokemonapp.view

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle

class ChartModel {

    fun getChartModel(): AAChartModel {
        return AAChartModel()
            .chartType(AAChartType.Column)
            .titleStyle(TITLE_STYLE_COLOR)
            .backgroundColor(android.R.color.transparent)
            .axesTextColor(AXES_STYLE_COLOR)
            .dataLabelsEnabled(true)
            .dataLabelsStyle(DATA_LABEL_STYLE_COLOR)
            .xAxisVisible(false)
            .yAxisVisible(false)
            .yAxisMax(255f)
    }

    companion object {

        private val WHITE_COLOR = "#FFFFFF"
        private val DATA_LABEL_STYLE_COLOR = AAStyle().color(WHITE_COLOR)
        private val TITLE_STYLE_COLOR = AAStyle().color(WHITE_COLOR)
        private val AXES_STYLE_COLOR = WHITE_COLOR
    }
}