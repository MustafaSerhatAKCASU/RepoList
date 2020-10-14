package com.repolist.network

import com.repolist.model.RepoModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DataAPI {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Single<List<RepoModel>>
}