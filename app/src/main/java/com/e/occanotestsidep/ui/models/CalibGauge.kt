package com.e.occanotestsidep.ui.models

data class CalibGauge(
    var name:String= "",
    var value: Float = 0f,
    var isSelected:Boolean = false
){
    override fun toString(): String {
        return "CalibGauge(name='$name', isSelected=$isSelected)"
    }
}