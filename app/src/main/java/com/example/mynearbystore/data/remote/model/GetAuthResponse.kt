package com.example.mynearbystore.data.remote.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetAuthResponse(

	@SerializedName("stores")
	val stores: List<StoresItem>? = mutableListOf(),

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class StoresItem(

	@SerializedName("store_id")
	val storeId: String? = null,

	@SerializedName("store_code")
	val storeCode: String? = null,

	@SerializedName("channel_name")
	val channelName: String? = null,

	@SerializedName("area_name")
	val areaName: String? = null,

	@SerializedName("address")
	val address: String? = null,

	@SerializedName("dc_name")
	val dcName: String? = null,

	@SerializedName("latitude")
	val latitude: String? = null,

	@SerializedName("region_id")
	val regionId: String? = null,

	@SerializedName("area_id")
	val areaId: String? = null,

	@SerializedName("account_id")
	val accountId: String? = null,

	@SerializedName("dc_id")
	val dcId: String? = null,

	@SerializedName("subchannel_id")
	val subchannelId: String? = null,

	@SerializedName("account_name")
	val accountName: String? = null,

	@SerializedName("store_name")
	val storeName: String? = null,

	@SerializedName("subchannel_name")
	val subchannelName: String? = null,

	@SerializedName("region_name")
	val regionName: String? = null,

	@SerializedName("channel_id")
	val channelId: String? = null,

	@SerializedName("longitude")
	val longitude: String? = null
) : Parcelable
