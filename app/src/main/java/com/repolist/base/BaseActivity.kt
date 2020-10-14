package com.repolist.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.repolist.ui.repolist.RepoListActivity
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity() {
    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        init()
        prepareToolbar()
        init(savedInstanceState)
    }

    open fun init() {}
    open fun init(savedInstanceState: Bundle?) {}

    private fun prepareToolbar() {
        toolbar.let {
            setSupportActionBar(it)
            if (this !is RepoListActivity) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (this !is RepoListActivity) {
            onBackPressed()
            return true
        }
        return super.onSupportNavigateUp()
    }
}