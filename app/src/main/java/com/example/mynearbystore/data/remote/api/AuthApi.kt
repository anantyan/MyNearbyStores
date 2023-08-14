package com.example.mynearbystore.data.remote.api

import com.example.mynearbystore.data.remote.model.GetAuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("login/loginTest")
    suspend fun login(
        @Field("username") username: String? = null,
        @Field("password") password: String? = null
    ) : Response<GetAuthResponse>
}