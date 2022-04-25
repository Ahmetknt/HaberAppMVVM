package com.ahmetkanat.habermvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetkanat.habermvvm.model.Haber
import com.ahmetkanat.habermvvm.roomdb.HaberDatabase

class DetayViewModel(application: Application) : BaseViewModel(application) {

    val haberLiveData = MutableLiveData<Haber>()


    fun getDataFromRoom(uuid : Int){

        val dao = HaberDatabase.getHaberDb(getApplication())!!.haberDAO()
        val haber = dao.getHaber(uuid)

        haberLiveData.value = haber

    }



}