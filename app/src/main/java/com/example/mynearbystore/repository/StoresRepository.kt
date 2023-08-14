package com.example.mynearbystore.repository

import com.example.mynearbystore.data.local.dao.StoresItemDao
import javax.inject.Inject

class StoresRepository @Inject constructor(
    private val storesDao: StoresItemDao
) {
    suspend fun updateStore(id: Int, checkList: Boolean = true) = storesDao.updateStore(id, checkList)

    fun getStore(id: Int) = storesDao.getStore(id)
}