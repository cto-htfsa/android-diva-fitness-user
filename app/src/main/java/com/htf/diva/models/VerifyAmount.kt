package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class VerifyAmount :Serializable {
    @SerializedName("message")
    @Expose
    private val message: String? = null

    @SerializedName("errors")
    @Expose
    private val errors: Errors? = null

}
class Errors: Serializable{
    @SerializedName("error")
    @Expose
    var error: ArrayList<ErrorList>? = null

}

class ErrorList:Serializable{

}



