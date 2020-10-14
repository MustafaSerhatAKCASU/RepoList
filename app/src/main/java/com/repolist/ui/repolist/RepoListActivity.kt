package com.repolist.ui.repolist

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.repolist.R
import com.repolist.base.BaseActivity
import com.repolist.model.RepoModel
import com.repolist.ui.repolist.adapter.RepoListAdapter
import kotlinx.android.synthetic.main.activity_repo_list.*


class RepoListActivity : BaseActivity() {
    override val layoutResourceId: Int = R.layout.activity_repo_list
    private lateinit var viewModel: RepoListViewModel
    private val adapter = RepoListAdapter(arrayListOf(), this)

    private var favoriteRepoList: ArrayList<RepoModel>? = ArrayList()
    private lateinit var sharedPreferences: SharedPreferences

    override fun init() {
        viewModel = ViewModelProvider(this).get(RepoListViewModel::class.java)
        sharedPreferences = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        rv_repo_list?.adapter = adapter

        btn_submit?.setOnClickListener { viewModel.refreshData(et_username.text.toString()) }
    }

    override fun onResume() {
        super.onResume()
        observeLiveData()

        favoriteRepoList = if (getList() == null) {
            ArrayList()
        } else {
            getList()
        }

        if (!et_username.text.isNullOrEmpty()) {
            viewModel.refreshData(et_username.text.toString())
        }
    }

    private fun observeLiveData() {
        viewModel.data.observe(this, { data ->
            data?.let {
                if (data.isNullOrEmpty()) {
                    empty_view.showEmpty()
                } else {
                    rv_repo_list.visibility = View.VISIBLE
                    for (repo in data) {
                        if (favoriteRepoList?.find { it.name == repo.name && it.owner.owner == repo.owner.owner } != null) {
                            repo.isFavorite = true
                        }
                    }
                    adapter.updateDataList(data)
                    empty_view.showContent()
                }
            }
        })

        viewModel.dataError.observe(this, { error ->
            error?.let {
                if (it) {
                    empty_view.showError()
                }
            }
        })

        viewModel.dataLoading.observe(this, { loading ->
            loading?.let {
                if (it) {
                    empty_view.showLoading()
                }
            }
        })
    }

    fun getList(): ArrayList<RepoModel>? {
        var arrayItems: ArrayList<RepoModel>? = ArrayList()
        val serializedObject = sharedPreferences.getString("favorite_repo_list", null)
        if (serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<RepoModel>>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }
        return arrayItems
    }
}