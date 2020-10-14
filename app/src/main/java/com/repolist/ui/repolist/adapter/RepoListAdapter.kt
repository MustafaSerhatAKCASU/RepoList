package com.repolist.ui.repolist.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.repolist.R
import com.repolist.model.RepoModel
import com.repolist.ui.repodetail.RepoDetailActivity
import kotlinx.android.synthetic.main.item_repo_list.view.*

class RepoListAdapter(private val repoList: ArrayList<RepoModel>, private val context: Context) : RecyclerView.Adapter<RepoListAdapter.DataViewHolder>() {

    class DataViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_repo_list, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.view.tv_repo_name.text = repoList[position].name
        holder.view.iv_favorite.visibility =
            if (repoList[position].isFavorite) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            RepoDetailActivity.start(context as Activity, repoList[position])
        }
    }

    fun updateDataList(newDataList: List<RepoModel>) {
        repoList.clear()
        repoList.addAll(newDataList)
        notifyDataSetChanged()
    }
}