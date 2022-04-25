package com.ahmetkanat.habermvvm.model

data class Response(
    val success : Boolean,
    val result : List<Haber>
)