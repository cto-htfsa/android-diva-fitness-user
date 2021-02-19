package com.htf.diva.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val `data`: T,
    @SerializedName("message")
    val message: String
)