package com.dicoding.githubsubmissionbaru.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubsubmissionbaru.data.helperfavorit.helper
import com.dicoding.githubsubmissionbaru.data.repository.Repository

class FactoryView private constructor(private val mApplication: Repository): ViewModelProvider.NewInstanceFactory() {
    companion object {
    @Volatile
    private var instance: FactoryView? = null
    fun getInstance(context: Context): FactoryView =
        instance ?: synchronized(this) {
            instance ?: FactoryView(helper.provideRepository(context))
        }.also { instance = it }
}

@Suppress("UNCHECKED_CAST")
override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FavoritView::class.java)) {
        return FavoritView(mApplication) as T
    } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
        return DetailViewModel(mApplication) as T
    } else if (modelClass.isAssignableFrom(MainViewModell::class.java)) {
        return MainViewModell(mApplication) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
}
}