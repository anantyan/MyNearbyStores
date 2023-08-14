package com.example.mynearbystore.repository

import androidx.room.withTransaction
import com.example.mynearbystore.data.local.dao.StoresItemDao
import com.example.mynearbystore.data.local.model.StoresItemLocal
import com.example.mynearbystore.data.remote.api.AuthApi
import com.example.mynearbystore.data.remote.model.GetAuthResponse
import com.example.mynearbystore.database.RoomDatabase
import com.example.mynearbystore.util.DataStoreManager
import com.example.mynearbystore.util.Resource
import com.example.mynearbystore.util.castFromRemoteToLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val storesDao: StoresItemDao,
    private val store: DataStoreManager,
    private val db: RoomDatabase
) {
    fun logout() = store.setLogged(false)

    fun logged() = store.getLogged()

    suspend fun login(username: String?, password: String?): Flow<Resource<GetAuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.login(username, password)
            if (response.body()?.status == "success") {
                response.body()?.let {
                    store.setLogged(true)
                    db.withTransaction {
                        storesDao.deleteStores()
                        storesDao.setStores(it.stores.castFromRemoteToLocal())
                    }
                    emit(Resource.Success(it))
                }
            } else {
                response.body()?.let {
                    throw Exception("${it.status} - ${it.message}")
                }
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }
}