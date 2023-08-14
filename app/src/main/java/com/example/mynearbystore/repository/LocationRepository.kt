package com.example.mynearbystore.repository

import android.annotation.SuppressLint
import android.location.Location
import com.example.mynearbystore.data.local.dao.StoresItemDao
import com.example.mynearbystore.data.remote.api.LocationApi
import com.example.mynearbystore.data.remote.model.GetLocationResponse
import com.example.mynearbystore.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val storesDao: StoresItemDao,
    private val locationApi: LocationApi
) {
    suspend fun searchLocation(query: String): Flow<Resource<GeoPoint>> = flow {
        emit(Resource.Loading())
        try {
            val response = locationApi.searchLocation(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(
                        GeoPoint(it[0].lat?.toDouble() ?: 0.0, it[0].lon?.toDouble() ?: 0.0)
                    ))
                }
            } else {
                response.errorBody()?.let {
                    throw Exception("Terjadi kesalahan!")
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return fusedLocationProviderClient.lastLocation.await()
    }

    fun getStores() = storesDao.getStores()
}