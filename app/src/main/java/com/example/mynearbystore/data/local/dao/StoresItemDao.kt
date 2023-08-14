package com.example.mynearbystore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynearbystore.data.local.model.StoresItemLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StoresItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setStores(items: List<StoresItemLocal>)

    @Query("SELECT * FROM tbl_stores")
    fun getStores() : Flow<List<StoresItemLocal>>

    @Query("DELETE FROM tbl_stores")
    suspend fun deleteStores()

    @Query("UPDATE tbl_stores SET checklist=:checkList WHERE id=:id")
    suspend fun updateStore(id: Int, checkList: Boolean)

    @Query("SELECT * FROM tbl_stores WHERE id=:id")
    fun getStore(id: Int): Flow<StoresItemLocal>
}