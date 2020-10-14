package com.repolist.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoModel(
    @SerializedName("name") var name: String,
    @SerializedName("stargazers_count") var starCount: Int,
    @SerializedName("open_issues_count") var openIssueCount: Int,
    @SerializedName("owner") var owner: Owner,
    @Expose var isFavorite: Boolean = false
) : Parcelable
