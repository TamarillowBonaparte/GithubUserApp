package com.dicoding.githubsubmissionbaru.data.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubsubmissionbaru.data.local.DataUser
import com.dicoding.githubsubmissionbaru.data.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(private val repositoryUser: Repository):ViewModel() {
    fun insertUser(favoritUser: DataUser){
        viewModelScope.launch {
            repositoryUser.insert(favoritUser)
        }
    }
    fun deleteUser(favoritUser: DataUser){
        viewModelScope.launch {
            repositoryUser.delete(favoritUser)
        }
    }
    fun getFavoritUser(username : String) : LiveData<DataUser>{
        return repositoryUser.getUser(username)
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return repositoryUser.getThemeSetting().asLiveData()
    }
}