package com.example.mynearbystore.util

object Constant {
    const val BASE_URL = "https://keraton.indward.com/api/sariroti_md/index.php/"
    const val MAP_URL = "https://nominatim.openstreetmap.org/"
    const val PERMISSION_COARSE_AND_FINE_LOCATION = 101
    const val PASSING_FROM_STORE_TO_STORE_DETAIL = "FROM_STORE_TO_STORE_DETAIL"
    const val PASSING_FROM_STORE_DETAIL_TO_STORE_DASHBOARD = "FROM_STORE_DETAIL_TO_STORE_DASHBOARD"
    val LIST_OF_PERMISSION_LOCATION = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
}