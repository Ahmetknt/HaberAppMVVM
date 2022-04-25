package com.ahmetkanat.habermvvm.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmetkanat.habermvvm.model.Haber
import com.ahmetkanat.habermvvm.roomdb.HaberDAO

@Database(entities = [Haber::class], version = 2)
abstract class HaberDatabase : RoomDatabase() {
    abstract fun haberDAO() : HaberDAO

    companion object{

        private var INSTANCE : HaberDatabase? = null

        fun getHaberDb(context : Context) : HaberDatabase?{

            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    HaberDatabase::class.java,"haberler.db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE

        }

    }

}