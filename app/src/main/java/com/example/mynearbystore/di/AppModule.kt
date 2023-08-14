package com.example.mynearbystore.di

import android.content.Context
import com.example.mynearbystore.data.local.dao.StoresItemDao
import com.example.mynearbystore.data.remote.api.AuthApi
import com.example.mynearbystore.data.remote.api.LocationApi
import com.example.mynearbystore.database.RoomDatabase
import com.example.mynearbystore.util.DataStoreManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOSMApi(@Named("OSM") retrofit: Retrofit): LocationApi {
        return retrofit.create(LocationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideStoresDao(roomDB: RoomDatabase): StoresItemDao {
        return roomDB.storesDao()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}