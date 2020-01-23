package com.letsrango.app.feature.home.data.service.model

import java.io.Serializable

data class HomeResponse(
    var home: HomeData,
    var data: ArrayList<CardDataItem> = arrayListOf()
) : Serializable


data class HomeData (
    var darkMode: Boolean = false,
    var categoryList: ArrayList<HomeCategoryItem> = arrayListOf()

) : Serializable

data class HomeCategoryItem (
    var id: Int = 0 ,
    var name: String = ""
) : Serializable

data class CardDataItem(
    var id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var btnText: String = "",
    var category: String = "",
    var status: Boolean = false,
    var picture: String = "",
    var price: Float = 0.0f

) : Serializable
