package com.dicoding.githubsubmissionbaru.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataUser::class], version = 1)
abstract class FavoritDatabase: RoomDatabase() {

    abstract fun FavoriteCendolDao(): FavoriteCendolDao

    companion object {
        @Volatile
        private var instance: FavoritDatabase? = null
        fun getFavoritDatabase (context: Context): FavoritDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoritDatabase::class.java,
                    "favorite.db"
                ).build()
            }
    }
}