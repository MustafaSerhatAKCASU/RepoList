package com.repolist.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.repolist.BuildConfig
import com.repolist.model.RepoModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIUrl {
    private val BASE_URL = "https://api.github.com/"

    private val client = OkHttpClient.Builder().let {
        it.retryOnConnectionFailure(true)
        it.connectTimeout(10, TimeUnit.SECONDS)
        it.readTimeout(30, TimeUnit.SECONDS)
        it.writeTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            it.addNetworkInterceptor(StethoInterceptor())
        }
        it
    }.build()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(DataAPI::class.java)

    fun getRepoList(user: String): Single<List<RepoModel>> {
        return api.listRepos(user)
    }
}