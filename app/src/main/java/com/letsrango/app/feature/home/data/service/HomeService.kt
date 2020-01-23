package com.letsrango.app.feature.home.data.service

import com.letsrango.app.feature.home.data.service.model.HomeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface HomeService {
    @GET("/masterclass")
    fun getHome(): Observable<HomeResponse>
}