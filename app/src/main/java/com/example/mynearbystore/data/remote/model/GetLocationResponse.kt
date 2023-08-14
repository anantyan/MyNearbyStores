package com.example.mynearbystore.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetLocationResponse(
    @SerializedName("lat")
    val lat: String? = null,

    @SerializedName("lon")
    val lon: String? = null,

    @SerializedName("display_name")
    val display_name: String? = null
) : Parcelable
