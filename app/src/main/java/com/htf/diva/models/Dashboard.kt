package com.htf.diva.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Dashboard:Serializable {

    var topBanners= ArrayList<Banner>()
    var trainers= ArrayList<Trainers>()
    var branchCentre= ArrayList<Branch>()

}