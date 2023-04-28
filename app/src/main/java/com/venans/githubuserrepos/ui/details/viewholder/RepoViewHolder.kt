package com.venans.githubuserrepos.ui.details.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.venans.githubuserrepos.databinding.ItemRepoBinding
import com.venans.githubuserrepos.model.Repo

/**
 * [RecyclerView.ViewHolder] implementation to inflate View for RecyclerView.
 * See [RepoListAdapter]]
 */
class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repo: Repo) {
        binding.repoFullName.text = repo.fullName
        binding.repoDescription.text = repo.description
    }
}
