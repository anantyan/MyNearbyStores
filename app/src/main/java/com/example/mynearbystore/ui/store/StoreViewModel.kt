package com.example.mynearbystore.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynearbystore.data.local.model.StoresItemLocal
import com.example.mynearbystore.data.remote.model.GetLocationResponse
import com.example.mynearbystore.repository.LocationRepository
import com.example.mynearbystore.util.Resource
import com.example.mynearbystore.util.haversine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _currentLocation = MutableLiveData<GeoPoint>()
    private val _stores = MutableLiveData<List<StoresItemLocal>>()
    private val _nearbyStores = MutableLiveData<List<StoresItemLocal>>()
    private val _searchLocation = MutableLiveData<Resource<GeoPoint>>()

    val currentLocation: LiveData<GeoPoint> = _currentLocation
    val nearbyStores: LiveData<List<StoresItemLocal>> = _nearbyStores
    val searchLocation: LiveData<Resource<GeoPoint>> = _searchLocation
    val isEmpty = MutableLiveData<Boolean>()

    fun getCurrentLocation() = viewModelScope.launch {
        val location = locationRepository.getCurrentLocation()
        location?.let {
            _currentLocation.postValue(GeoPoint(it.latitude, it.longitude))
        }
    }

    fun getStores() = viewModelScope.launch {
        locationRepository.getStores().collect {
            _stores.postValue(it)
        }
    }

    fun nearbyStores(geoPoint: GeoPoint) {
        val items = _stores.value?.map { store ->
            val distance = haversine(geoPoint.latitude, geoPoint.longitude, store.latitude?.toDouble() ?: 0.0, store.longitude?.toDouble() ?: 0.0) / 1000 // Untuk satuan kilometer
            store.distance = distance
            store
        }?.filter { it.distance!! <= 0.1 } // Radius 100m

        _nearbyStores.postValue(items ?: mutableListOf())
        isEmpty.postValue(items.isNullOrEmpty())
    }

    fun searchLocation(query: String) = viewModelScope.launch {
        locationRepository.searchLocation(query).collect {
            _searchLocation.postValue(it)
        }
    }
}