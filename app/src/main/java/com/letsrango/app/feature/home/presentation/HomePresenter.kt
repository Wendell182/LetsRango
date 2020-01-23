package com.letsrango.app.feature.home.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.letsrango.app.feature.home.data.HomeRepository
import com.letsrango.app.feature.home.data.service.model.CardDataItem
import com.letsrango.app.feature.home.data.service.model.HomeResponse
import com.letsrango.app.feature.home.domain.HomeContract
import com.letsrango.app.feature.home.domain.HomeInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(private var activity: HomeActivity) : HomeContract.IPresenter {
    var interactor: HomeInteractor = HomeInteractor()
    private val ioScheduler: Scheduler = Schedulers.io()
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()


    @SuppressLint("CheckResult")
    override fun initHome(
        result: (response: HomeResponse) -> Unit
    ) {
        interactor.getHome()
            .observeOn(mainScheduler)
            .subscribeOn(ioScheduler)
            .doOnSubscribe {}
            .doFinally {}
            .subscribe({
                result(it)

            }, {
                Log.i("TTT", it.message)
            })
    }

    override fun showBottomSheet(cardDataItem: CardDataItem, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}