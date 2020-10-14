package com.repolist.ui.repodetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.repolist.R
import com.repolist.base.BaseActivity
import com.repolist.model.RepoModel
import kotlinx.android.synthetic.main.activity_repo_detail.*


class RepoDetailActivity : BaseActivity() {
    override val layoutResourceId: Int = R.layout.activity_repo_detail

    private var repo: RepoModel? = null
    private var favoriteRepoList: ArrayList<RepoModel>? = ArrayList()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun <T> setList(key: String?, list: List<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
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

    override fun init() {
        sharedPreferences = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        repo = intent?.extras?.getParcelable(REPO_MODEL)!!
        title = repo?.name

        favoriteRepoList = if (getList() == null) {
            ArrayList()
        } else {
            getList()
        }

        Glide.with(this).load(repo?.owner?.avatarUrl).into(iv_avatar!!)
        tv_owner?.text = repo?.owner?.owner
        tv_open_issues?.text = repo?.openIssueCount.toString()
        tv_stars?.text = repo?.starCount.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val settingsItem = menu!!.findItem(R.id.action_favorite)
        if (repo?.isFavorite!!) {
            settingsItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_star)
        } else {
            settingsItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (favoriteRepoList?.find { it.name == repo?.name && it.owner.owner == repo?.owner?.owner } != null) {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
                    favoriteRepoList?.remove(repo)
                    setList("favorite_repo_list", favoriteRepoList)
                    Toast.makeText(this, "Removed from favorite list", Toast.LENGTH_SHORT).show()
                } else {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_star)
                    repo?.let {
                        it.isFavorite = true
                        favoriteRepoList?.add(it)
                    }
                    setList("favorite_repo_list", favoriteRepoList)
                    Toast.makeText(this, "Added to favorite list", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    companion object {
        const val REPO_MODEL = "repo_model"

        fun start(activity: Activity, repoModel: RepoModel) {
            activity.startActivity(Intent(activity, RepoDetailActivity::class.java).apply {
                putExtra(REPO_MODEL, repoModel)
            })
        }
    }
}