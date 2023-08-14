package com.example.mynearbystore.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynearbystore.data.remote.model.GetAuthResponse
import com.example.mynearbystore.repository.AuthRepository
import com.example.mynearbystore.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val logged = authRepository.logged()

    private var _login = MutableLiveData<Resource<GetAuthResponse>>()
    val login: LiveData<Resource<GetAuthResponse>> = _login

    fun login(username: String?, password: String?) = viewModelScope.launch {
        authRepository.login(username, password).collect {
            _login.postValue(it)
        }
    }
}