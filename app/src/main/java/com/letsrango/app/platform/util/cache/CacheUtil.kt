package com.letsrango.app.platform.util.cache

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.letsrango.app.feature.home.presentation.HomeActivity


class CacheUtil {
    companion object {
        var SP_CACHE = "SP_CACHE"
        var ITEM_QT = "ITEM_QT"
        var ITEM_TOTAL = "ITEM_TOTOAL"
    }

    var instance: CacheUtil? = null
    lateinit var context: Context
    var editor: SharedPreferences.Editor? = null
    var prefs: SharedPreferences? = null

    @SuppressLint("CommitPrefEdits")
    fun newInstance(context: Context): CacheUtil {

        return CacheUtil().apply {
            this.context = context
            prefs = (this.context as HomeActivity).getSharedPreferences(SP_CACHE, MODE_PRIVATE)
            editor = this.prefs!!.edit()
            instance = this
        }

    }


    fun setItemQt(cardItemId: Int, value: Int) {

        if (editor == null) {
            editor =
                (this.context as HomeActivity).getSharedPreferences(SP_CACHE, MODE_PRIVATE).edit()
        }
        editor?.putInt(cardItemId.toString(), value)
        editor?.apply()


    }

    fun getItemQt(cardItemId: Int): Int? {
        if (prefs == null) {
            prefs = (this.context as HomeActivity).getSharedPreferences(ITEM_TOTAL, MODE_PRIVATE)
        }
        return prefs?.getInt(cardItemId.toString(), 0)

    }

    fun clearItem(){
        if (editor == null) {
            editor =
                (this.context as HomeActivity).getSharedPreferences(SP_CACHE, MODE_PRIVATE).edit()
        }

        editor?.clear()
        editor?.commit()

    }




}