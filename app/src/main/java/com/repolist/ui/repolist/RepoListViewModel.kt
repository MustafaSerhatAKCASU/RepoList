package com.repolist.ui.repolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repolist.model.RepoModel
import com.repolist.network.APIUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RepoListViewModel : ViewModel() {

    private val dataApiService = APIUrl()
    private val disposable = CompositeDisposable()

    val data = MutableLiveData<List<RepoModel>>()
    val dataError = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()

    fun refreshData(user: String) {
        getRepoListFromApi(user)
    }

    private fun getRepoListFromApi(user: String) {
        dataLoading.value = true

        disposable.add(
            dataApiService.getRepoList(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<RepoModel>>() {
                    override fun onSuccess(t: List<RepoModel>) {
                        data.value = t
                        dataError.value = false
                        dataLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        dataLoading.value = false
                        dataError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }
}