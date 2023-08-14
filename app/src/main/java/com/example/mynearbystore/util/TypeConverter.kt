package com.example.mynearbystore.util

import com.example.mynearbystore.data.local.model.StoresItemLocal
import com.example.mynearbystore.data.remote.model.StoresItem

fun List<StoresItem>?.castFromRemoteToLocal(): List<StoresItemLocal> {
    val list = mutableListOf<StoresItemLocal>()
    this?.forEach {
        list.add(
            StoresItemLocal(
                storeId = it.storeId,
                storeCode = it.storeCode,
                storeName = it.storeName,
                channelName = it.channelName,
                channelId = it.channelId,
                areaId = it.areaId,
                areaName = it.areaName,
                address = it.address,
                dcId = it.dcId,
                dcName = it.dcName,
                latitude = it.latitude,
                longitude = it.longitude,
                regionId = it.regionId,
                regionName = it.regionName,
                accountId = it.accountId,
                accountName = it.accountName,
                subchannelId = it.subchannelId,
                subchannelName = it.subchannelName
            )
        )
    }
    return list
}

fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return R * c * 1000
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)