package com.ahmetkanat.habermvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ahmetkanat.habermvvm.model.Haber
import com.ahmetkanat.habermvvm.model.Response
import com.ahmetkanat.habermvvm.network.APIUtils
import com.ahmetkanat.habermvvm.roomdb.HaberDatabase
import com.ahmetkanat.habermvvm.utils.CustomSharedPreferences
import retrofit2.Call
import retrofit2.Callback


class HaberViewModel(application: Application) : BaseViewModel(application) {
    private var customPreferences = CustomSharedPreferences(getApplication())
    val haberler = MutableLiveData<List<Haber>>()
    val haberError = MutableLiveData<Boolean>()
    val haberLoading = MutableLiveData<Boolean>()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    fun refreshData(){
        haberLoading.value = true
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI("economy","tr")
            getDataFromAPI("sport","tr")
            getDataFromAPI("technology","tr")
            getDataFromAPI("general","tr")
        }
    }

    fun refreshFromAPI(){
        getDataFromAPI("economy","tr")
        getDataFromAPI("sport","tr")
        getDataFromAPI("technology","tr")
        getDataFromAPI("general","tr")
    }

    private fun getDataFromSQLite(){
        val haber = HaberDatabase.getHaberDb(getApplication())!!.haberDAO().getAllHaber()
        showHaber(haber)
    }

    private fun showHaber(haberList : List<Haber>){

        haberler.value = haberList
        haberError.value = false
        haberLoading.value = false
    }


    fun getDataFromAPI(tag : String,country : String){

        APIUtils.haberDAO().getHaberDAO(tag,country).enqueue(
            object : Callback<Response>{
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    val tempList = response.body()?.result
                    tempList?.let {
                        haberler.value = it as ArrayList<Haber>
                    }
                    haberler.value?.let { insertToRoom(it) }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    haberError.value = true
                    haberLoading.value = false
                }

            }
        )
    }

    fun insertToRoom(haberList : List<Haber>){

        val dao = HaberDatabase.getHaberDb(getApplication())!!.haberDAO()
        dao.deleteAllHaber()
        val listLong = dao.insertHaber(*haberList.toTypedArray())
        var i = 0
        while (i < haberList.size){
            haberList[i].uuid = listLong[i].toInt()
            i += 1
        }
        showHaber(haberList)
        customPreferences.saveTime(System.nanoTime())
    }



}