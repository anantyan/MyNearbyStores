package com.example.mynearbystore

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration

@HiltAndroidApp
class MyApplications : Application() {

    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().load(applicationContext, applicationContext.getSharedPreferences(applicationContext.packageName + "_preferences", Context.MODE_PRIVATE))
    }
}