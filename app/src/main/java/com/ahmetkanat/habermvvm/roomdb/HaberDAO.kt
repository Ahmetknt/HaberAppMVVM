package com.ahmetkanat.habermvvm.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ahmetkanat.habermvvm.model.Haber

@Dao
interface HaberDAO {

    @Insert
    fun insertHaber(vararg haber : Haber) : List<Long>

    @Query("SELECT * FROM haber WHERE uuid = :haberId")
    fun getHaber(haberId : Int) : Haber

    @Query("SELECT * FROM haber")
    fun getAllHaber() : List<Haber>

    @Query("DELETE FROM haber")
    fun deleteAllHaber()


}