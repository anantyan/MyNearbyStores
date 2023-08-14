package com.example.mynearbystore.ui.store_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynearbystore.data.local.model.StoresItemLocal
import com.example.mynearbystore.repository.StoresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val storesRepository: StoresRepository
) : ViewModel() {
    private var _getItem = MutableLiveData<StoresItemLocal>()
    private var _checkItem = MutableLiveData<String>()

    val getItem: LiveData<StoresItemLocal> = _getItem
    val checkItem: LiveData<String> = _checkItem

    fun getItem(id: Int) = viewModelScope.launch {
        storesRepository.getStore(id).collect {
            _getItem.postValue(it)
        }
    }

    fun checkItem(id: Int) = viewModelScope.launch {
        storesRepository.updateStore(id, false)
        _checkItem.postValue("Anda telah membatalkan kunjungan anda! :(")
    }
}