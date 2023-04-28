package com.venans.githubuserrepos.ui.main.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.venans.githubuserrepos.R
import com.venans.githubuserrepos.databinding.ItemUserBinding
import com.venans.githubuserrepos.model.User


/**
 * [RecyclerView.ViewHolder] implementation to inflate View for RecyclerView.
 * See [UserListAdapter]]
 */
class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User, onItemClicked: (User, ImageView) -> Unit) {
        binding.userLoginName.text = user.login
        binding.userUrl.text = user.url
        binding.userAvatarImage.load(user.avatarUrl) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        binding.root.setOnClickListener {
            onItemClicked(user, binding.userAvatarImage)
        }
    }
}
