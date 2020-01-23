package com.letsrango.app.feature.home.domain

import android.content.Context
import com.letsrango.app.feature.home.data.service.model.CardDataItem
import com.letsrango.app.feature.home.data.service.model.HomeResponse
import com.letsrango.app.feature.home.presentation.HomeActivity
import io.reactivex.Observable

interface HomeContract {
    interface IView{
     fun attachPresenter(view: HomeActivity)
    }

    interface  IPresenter{
        fun initHome(result: (response: HomeResponse) -> Unit)
        fun showBottomSheet(cardDataItem: CardDataItem,context: Context)

    }

    interface IInteractor{
        fun getHome(): Observable<HomeResponse>
    }

    interface IRepository{
        fun getHome(interector:HomeInteractor): Observable<HomeResponse>

    }
}
