package com.example.mynearbystore.data.remote.api

import com.example.mynearbystore.data.remote.model.GetLocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("search")
    suspend fun searchLocation(
        @Query("q") query: String = "",
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ) : Response<List<GetLocationResponse>>
}