package com.dicoding.githubsubmissionbaru.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCendolDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoritUser: DataUser)

    @Delete
    suspend fun delete(favoriteUser: DataUser)

    @Query("SELECT * FROM DataUser")
    fun getFavoritedUsers(): LiveData<List<DataUser>>

    @Query("SELECT * FROM DataUser WHERE username = :username")
    fun getFavoriteUser(username: String): LiveData<DataUser>
}