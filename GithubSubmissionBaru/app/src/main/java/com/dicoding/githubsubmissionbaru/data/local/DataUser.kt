package com.dicoding.githubsubmissionbaru.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class DataUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "",
    var avatarUrl: String? = null,
): Parcelable