package com.example.mynearbystore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynearbystore.data.local.dao.StoresItemDao
import com.example.mynearbystore.data.local.model.StoresItemLocal

@Database(entities = [
    StoresItemLocal::class
], version = 4, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun storesDao(): StoresItemDao
}