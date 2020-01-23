package com.letsrango.app.feature.home.presentation.adapter

import android.content.Context
import android.text.BoringLayout
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.letsrango.app.feature.home.data.service.model.CardDataItem
import com.letsrango.app.feature.home.presentation.HomeActivity
import com.letsrango.app.platform.bottomsheet.CustomBottomSheet
import com.letsrango.app.platform.extensions.getScreenHeight
import com.letsrango.app.platform.extensions.parseMoney
import com.letsrango.app.platform.util.cache.CacheUtil
import kotlinx.android.synthetic.main.bottomsheet_cart.*
import kotlinx.android.synthetic.main.bottomsheet_cart.view.*
import kotlinx.android.synthetic.main.card_layout.view.*
import kotlinx.android.synthetic.main.checkout_layout.view.*
import kotlin.random.Random

class CheckoutAdapter(private var context: Context, private var list: ArrayList<CardDataItem>) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {
    var mActivity: HomeActivity = context as HomeActivity
    lateinit var cacheUtil: CacheUtil

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.checkout_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        var item = list[position]
        cacheUtil = CacheUtil().newInstance(mActivity)



        var qt = cacheUtil.getItemQt(item.id)
        holder.checkoutBadge.setText(qt.toString())
        holder.checkoutTitle.setText(qt.toString() + "x " + item.price.parseMoney())

        Glide.with(context)
            .load(item.picture)
            .centerCrop()
            .into(holder.checkoutPic)


    }






    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var checkoutTitle = itemView.txtTitle as TextView
        internal var checkoutBadge = itemView.txtBadge as TextView
        internal var checkoutPic = itemView.imageTest as ImageView

    }

    lateinit var view: View
}