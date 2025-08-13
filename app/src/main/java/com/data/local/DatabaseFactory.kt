package com.data.local

import android.content.Context
import androidx.room.Room

 object DatabaseFactory {

    fun create(context: Context): OccasionDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            OccasionDatabase::class.java,
            "occasion_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
}