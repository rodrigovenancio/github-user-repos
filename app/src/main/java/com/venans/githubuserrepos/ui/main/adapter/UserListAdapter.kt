package  com.venans.githubuserrepos.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.venans.githubuserrepos.databinding.ItemUserBinding
import com.venans.githubuserrepos.model.User
import com.venans.githubuserrepos.ui.main.viewholder.UserViewHolder

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [User] along with [UserViewHolder]
 * @param onItemClicked which will receive callback when item is clicked.
 */
class UserListAdapter(
    private val onItemClicked: (User, ImageView) -> Unit
) : ListAdapter<User, UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}
