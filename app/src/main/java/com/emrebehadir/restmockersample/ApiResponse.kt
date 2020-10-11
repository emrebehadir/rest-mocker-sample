package com.emrebehadir.restmockersample

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("userId") val userId : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("message") val completed : Boolean
)
