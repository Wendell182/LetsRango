package com.letsrango.app.feature.home.data

import com.letsrango.app.feature.home.data.service.model.HomeResponse
import com.letsrango.app.feature.home.domain.HomeContract
import com.letsrango.app.feature.home.domain.HomeInteractor
import io.reactivex.Observable

class HomeRepository: HomeContract.IRepository {
    override fun getHome(homeInteractor:HomeInteractor): Observable<HomeResponse> {
        return homeInteractor.getHome()
    }
}