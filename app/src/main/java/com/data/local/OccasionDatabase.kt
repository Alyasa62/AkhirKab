package com.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [OccasionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class OccasionDatabase: RoomDatabase() {

    abstract fun occasionDao(): OccasionDao


}