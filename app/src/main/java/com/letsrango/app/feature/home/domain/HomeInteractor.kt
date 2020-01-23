package com.letsrango.app.feature.home.domain

import com.letsrango.app.feature.home.data.service.HomeService
import com.letsrango.app.feature.home.data.service.model.HomeResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeInteractor : HomeContract.IInteractor {
    var retrofit = Retrofit.Builder()
        .baseUrl("http://demo1043068.mockable.io")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    override fun getHome(): Observable<HomeResponse> {
        return retrofit.create(HomeService::class.java).getHome()

    }
}