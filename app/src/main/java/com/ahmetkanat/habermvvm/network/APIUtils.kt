package com.ahmetkanat.habermvvm.network

import com.ahmetkanat.habermvvm.utils.Constants.BASE_URL

class APIUtils {

    companion object{
        fun haberDAO() : HaberDao{
            return RetrofitClient.getClient(BASE_URL).create(HaberDao::class.java)
        }
    }
}