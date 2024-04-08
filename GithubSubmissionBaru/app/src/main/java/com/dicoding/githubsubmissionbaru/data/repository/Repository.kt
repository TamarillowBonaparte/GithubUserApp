package com.dicoding.githubsubmissionbaru.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubsubmissionbaru.data.local.DataUser
import com.dicoding.githubsubmissionbaru.data.local.FavoriteCendolDao
import com.dicoding.githubsubmissionbaru.data.setting.Setting
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository private constructor(private val favDao: FavoriteCendolDao, private val preferences: Setting) {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            favoriteCendolDao: FavoriteCendolDao,
            seting: Setting,

            ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(favoriteCendolDao, seting)
            }.also { instance = it }
    }


    suspend fun insert(favoritUser: DataUser) {
        favDao.insert(favoritUser)
    }

    suspend fun delete(favoritUser: DataUser) {
        favDao.delete(favoritUser)
    }

    fun getUser(username: String): LiveData<DataUser> {
        return favDao.getFavoriteUser(username)
    }

    fun getUsers(): LiveData<List<DataUser>> {
        return favDao.getFavoritedUsers()
    }

    fun getThemeSetting(): Flow<Boolean> {
        return preferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(darkModeActive: Boolean) {
        preferences.saveThemeSetting(darkModeActive)
    }
}