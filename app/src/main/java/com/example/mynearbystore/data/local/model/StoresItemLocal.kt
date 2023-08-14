package com.example.mynearbystore.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_stores")
data class StoresItemLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "store_id")
    val storeId: String? = null,

    @ColumnInfo(name = "store_code")
    val storeCode: String? = null,

    @ColumnInfo(name = "channel_name")
    val channelName: String? = null,

    @ColumnInfo(name = "area_name")
    val areaName: String? = null,

    @ColumnInfo(name = "address")
    val address: String? = null,

    @ColumnInfo(name = "dc_name")
    val dcName: String? = null,

    @ColumnInfo(name = "latitude")
    val latitude: String? = null,

    @ColumnInfo(name = "region_id")
    val regionId: String? = null,

    @ColumnInfo(name = "area_id")
    val areaId: String? = null,

    @ColumnInfo(name = "account_id")
    val accountId: String? = null,

    @ColumnInfo(name = "dc_id")
    val dcId: String? = null,

    @ColumnInfo(name = "subchannel_id")
    val subchannelId: String? = null,

    @ColumnInfo(name = "account_name")
    val accountName: String? = null,

    @ColumnInfo(name = "store_name")
    val storeName: String? = null,

    @ColumnInfo(name = "subchannel_name")
    val subchannelName: String? = null,

    @ColumnInfo(name = "region_name")
    val regionName: String? = null,

    @ColumnInfo(name = "channel_id")
    val channelId: String? = null,

    @ColumnInfo(name = "longitude")
    val longitude: String? = null,

    @ColumnInfo(name = "checklist")
    val checklist: Boolean? = false,

    @ColumnInfo(name = "distance")
    var distance: Double? = null
)
