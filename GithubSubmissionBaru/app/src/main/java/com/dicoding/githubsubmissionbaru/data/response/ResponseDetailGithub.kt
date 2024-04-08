package com.dicoding.githubsubmissionbaru.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseDetailGithub (
    val login: String,
    val followers: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    val following: Int,
    val name: String
): Parcelable