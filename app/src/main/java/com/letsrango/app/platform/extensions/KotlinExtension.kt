package com.letsrango.app.platform.extensions

import android.util.DisplayMetrics
import com.letsrango.app.feature.home.data.service.model.CardDataItem
import com.letsrango.app.feature.home.data.service.model.HomeResponse
import com.letsrango.app.feature.home.presentation.HomeActivity
import java.text.NumberFormat


lateinit var tempResponse:HomeResponse

fun HomeResponse.customFilter(query:String): HomeResponse {

    tempResponse = this

        var searchHomeResponse =  tempResponse .data.filter {
            it.category.toLowerCase().contains(query)
        }

         return ( tempResponse ?.apply {
            data = searchHomeResponse as ArrayList<CardDataItem>
        })


}

fun Float.parseMoney(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    return format.format(this)
}

fun HomeActivity.getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val height = displayMetrics.heightPixels
    val width = displayMetrics.widthPixels

    return height
}

fun HomeActivity.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val height = displayMetrics.heightPixels
    val width = displayMetrics.widthPixels

    return width
}
