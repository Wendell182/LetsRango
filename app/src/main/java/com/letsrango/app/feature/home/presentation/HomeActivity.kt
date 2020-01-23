package com.letsrango.app.feature.home.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.letsrango.app.feature.home.data.service.model.CardDataItem
import com.letsrango.app.feature.home.data.service.model.HomeResponse
import com.letsrango.app.feature.home.domain.HomeContract
import com.letsrango.app.feature.home.presentation.adapter.CheckoutAdapter
import com.letsrango.app.feature.home.presentation.adapter.HomeAdapter
import com.letsrango.app.platform.bottomsheet.CustomBottomSheet
import com.letsrango.app.platform.extensions.customFilter
import com.letsrango.app.platform.extensions.getScreenHeight
import com.letsrango.app.platform.extensions.parseMoney
import com.letsrango.app.platform.util.cache.CacheUtil
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottomsheet_checkout.*
import kotlin.random.Random


class HomeActivity : AppCompatActivity(), HomeContract.IView {
    lateinit var presenter: HomePresenter

    init {
        presenter = HomePresenter(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_home)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        presenter.initHome() {
            testRequest(it)
        }

        btnCerv.setOnClickListener {
            presenter.initHome() {
                testRequest(it.customFilter("cerveja")!!, true)
            }

        }

        btnSuco.setOnClickListener {
            presenter.initHome() {
                testRequest(it.customFilter("suco")!!, true)
            }

        }

        btnLacarte.setOnClickListener {
            presenter.initHome() {
                testRequest(it.customFilter("lanches")!!, true)
            }

        }

        btnAll.setOnClickListener {
            presenter.initHome() {
                testRequest(it)
            }

        }

        btnCarrinho.setOnClickListener() {
            getBottomSheetCart()


        }


    }

    override fun onResume() {
        super.onResume()
        presenter.initHome() {
            testRequest(it)

            getCart()
        }
    }

    var lastHomeResponse: HomeResponse? = null


    companion object {
        fun newInstance(context: Context): Intent {
            var intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }

    override fun attachPresenter(view: HomeActivity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun testRequest(homeResponse: HomeResponse, fromFilter: Boolean = false) {

        var homeAdapter: HomeAdapter = HomeAdapter(this, homeResponse.data)

        recyclerView.layoutManager = GridLayoutManager(this, 1)

        recyclerView.adapter = homeAdapter

        homeAdapter.notifyDataSetChanged()

        if (!fromFilter) {
            lastHomeResponse = homeResponse
        }

    }


    fun getBottomSheetCart() {

        var bottomSheet = CustomBottomSheet.newInstance(this)

        val contentView = View.inflate(this, R.layout.bottomsheet_checkout, null)
        bottomSheet.setContentView(contentView)
        var mBehavior = BottomSheetBehavior.from(contentView.parent as View);
        mBehavior.peekHeight = ((this).getScreenHeight() * 1).toInt()

        var listCheckout = arrayListOf<CardDataItem>()

        listCheckout = lastHomeResponse!!.data.filter {
            CacheUtil().newInstance(this).getItemQt(it.id)!! > 0

        } as ArrayList<CardDataItem>

        var adapter = CheckoutAdapter(this, listCheckout)

        bottomSheet.picList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        bottomSheet.picList.adapter = adapter


        adapter.notifyDataSetChanged()

        var hash = "#XPTO-" + Random.nextInt(9999999, 99999999)
        bottomSheet.txtNumPed.setText(hash)


        var qtTotal = 0
        var priceTotal = 0f
        listCheckout.forEach {
            qtTotal += CacheUtil().newInstance(this).getItemQt(it.id)!!
            priceTotal += (CacheUtil().newInstance(this).getItemQt(it.id)!! * it.price).toFloat()

        }

        bottomSheet.valorTot.setText(priceTotal.parseMoney())
        bottomSheet.qtdTot.setText(qtTotal.toString() + "x Itens")


        bottomSheet.show()

        if (qtTotal == 0) {
            bottomSheet.btnSend.isEnabled = false
            bottomSheet.cardNumPed.visibility = View.GONE
        } else {
            bottomSheet.btnSend.isEnabled = true
            bottomSheet.cardNumPed.visibility = View.VISIBLE
        }

        bottomSheet.btnSend.setOnClickListener {
            CacheUtil().newInstance(this).clearItem()
            bottomSheet.dismiss()
        }


    }


    fun getCart() {
        if (lastHomeResponse != null) {
            var totalList = lastHomeResponse!!.data.filter {
                CacheUtil().newInstance(this).getItemQt(it.id)!! > 0
            }

            if (totalList.isNotEmpty()) {
                btnCarrinho.show()
            } else {
                btnCarrinho.hide()
            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 500) {
            (recyclerView.adapter as HomeAdapter).clearProgressBar()
        }
    }
}

