package com.example.mynearbystore.ui.profile

import androidx.lifecycle.ViewModel
import com.example.mynearbystore.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val logout = authRepository.logout()
}