package com.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [OccasionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OccasionDatabase: RoomDatabase() {

    abstract fun occasionDao(): OccasionDao

    companion object {
        @Volatile
        private var INSTANCE: OccasionDatabase? = null

        fun getDatabase( context: Context): OccasionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.
                databaseBuilder(
                    context = context,
                    klass = OccasionDatabase::class.java,
                    name = "occasion_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}