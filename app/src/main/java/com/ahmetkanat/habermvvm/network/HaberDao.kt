package com.ahmetkanat.habermvvm.network

import com.ahmetkanat.habermvvm.model.Response
import com.ahmetkanat.habermvvm.utils.Constants.AUTHORIZATION
import com.ahmetkanat.habermvvm.utils.Constants.CONTENT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HaberDao {

        @Headers(
            "authorization: ${AUTHORIZATION}",
            "content-type: ${CONTENT}"
        )
        @GET("getNews")
        fun getHaberDAO(
            @Query("tag") tag : String,
            @Query("country") country : String
        ) : Call<Response>

}