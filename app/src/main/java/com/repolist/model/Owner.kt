package com.repolist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    @SerializedName("avatar_url") var avatarUrl: String,
    @SerializedName("login") var owner: String,
) : Parcelable