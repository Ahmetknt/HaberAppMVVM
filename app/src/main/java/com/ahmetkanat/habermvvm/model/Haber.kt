package com.ahmetkanat.habermvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "haber")
data class Haber(
    @ColumnInfo(name = "key")
    val key : String,
    @ColumnInfo(name = "url")
    val url : String,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "image")
    val image : String,
    @ColumnInfo(name = "source")
    val source : String,
    @ColumnInfo(name = "name")
    val name : String
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}