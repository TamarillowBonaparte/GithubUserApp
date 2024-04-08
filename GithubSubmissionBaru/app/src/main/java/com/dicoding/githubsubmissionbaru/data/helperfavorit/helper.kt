package com.dicoding.githubsubmissionbaru.data.helperfavorit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.githubsubmissionbaru.data.local.FavoritDatabase
import com.dicoding.githubsubmissionbaru.data.repository.Repository
import com.dicoding.githubsubmissionbaru.data.setting.Setting

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object helper {
    fun provideRepository(context: Context): Repository {
        val database = FavoritDatabase.getFavoritDatabase(context)
        val dao = database.FavoriteCendolDao()
        val pref = Setting.getInstance(context.settingsDataStore)
        return Repository.getInstance(dao, pref)
    }
}
