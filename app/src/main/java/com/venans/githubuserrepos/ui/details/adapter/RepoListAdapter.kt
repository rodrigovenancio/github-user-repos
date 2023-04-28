package  com.venans.githubuserrepos.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.venans.githubuserrepos.databinding.ItemRepoBinding
import com.venans.githubuserrepos.databinding.ItemUserBinding
import com.venans.githubuserrepos.model.Repo
import com.venans.githubuserrepos.model.User
import com.venans.githubuserrepos.ui.details.viewholder.RepoViewHolder
import com.venans.githubuserrepos.ui.main.viewholder.UserViewHolder

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [Repo] along with [RepoViewHolder]
 */
class RepoListAdapter() : ListAdapter<Repo, RepoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepoViewHolder(
        ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }
}
