package com.example.mynearbystore.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.mynearbystore.util.Constant.LIST_OF_PERMISSION_LOCATION
import com.example.mynearbystore.util.Constant.PERMISSION_COARSE_AND_FINE_LOCATION
import com.vmadalin.easypermissions.EasyPermissions

fun requestLocation(host: Activity) {
    EasyPermissions.requestPermissions(
        host = host,
        rationale = "Fitur ini tidak akan bisa jalan tanpa adanya izin lokasi!",
        requestCode = PERMISSION_COARSE_AND_FINE_LOCATION,
        perms = LIST_OF_PERMISSION_LOCATION
    )
}