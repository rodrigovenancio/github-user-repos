package com.venans.githubuserrepos.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.venans.githubuserrepos.databinding.ActivityUserDetailsBinding
import com.venans.githubuserrepos.model.State
import com.venans.githubuserrepos.model.User
import com.venans.githubuserrepos.ui.base.BaseActivity
import com.venans.githubuserrepos.ui.details.adapter.RepoListAdapter
import com.venans.githubuserrepos.ui.main.adapter.UserListAdapter
import com.venans.githubuserrepos.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserDetailsActivity : BaseActivity<UserDetailsViewModel, ActivityUserDetailsBinding>() {

    @Inject
    lateinit var viewModelFactory: UserDetailsViewModel.UserDetailsViewModelFactory

    private fun getUserDetailedInfo(userLogin: String) = mViewModel.getUser(userLogin)

    private val mAdapter = RepoListAdapter()

    override val mViewModel: UserDetailsViewModel by viewModels {
        val userId = intent.extras?.getLong(KEY_USER_ID)
            ?: throw IllegalArgumentException("`User` must be non-null")
        val userLogin = intent.extras?.getString(KEY_USER_LOGIN)
            ?: throw IllegalArgumentException("`User` must be non-null")

        UserDetailsViewModel.provideFactory(viewModelFactory,  userId, userLogin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        observeUser()
        observeRepos()
    }

    override fun onStart() {
        super.onStart()
        initUser()
    }

    private fun initUser() {
        mViewModel.user.observe(this) { user ->
            mViewBinding.userContent.apply {
                userLoginName.text = user.login
                userUrl.text = user.url
                user.login?.let {
                    getUserDetailedInfo(it)
                    getRepos(it)
                }
            }
            mViewBinding.userAvatarImage.load(user.avatarUrl)
        }
    }

    private fun initView() {
        mViewBinding.run {
            userContent.reposRecyclerView.adapter = mAdapter
        }
    }

    private fun getRepos(userLogin: String) = mViewModel.getRepos(userLogin)

    private fun observeRepos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.repos.collect { state ->
                    when (state) {
                        is State.Loading -> mViewBinding.userContent.progressCircular.visibility = View.VISIBLE
                        is State.Success -> {
                            if (state.data.isNotEmpty()) {
                                mAdapter.submitList(state.data.toMutableList())
                                mViewBinding.userContent.reposRecyclerView.visibility = View.VISIBLE
                                mViewBinding.userContent.progressCircular.visibility = View.GONE
                            }
                        }
                        is State.Error -> {
                            showToast(state.message)
                            mViewBinding.userContent.progressCircular.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun observeUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.userDetailedInfo.collect { state ->
                    when (state) {
                        is State.Success -> {
                            displayUserInfo(state.data)
                        }
                        is State.Error -> {
                            showToast(state.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun displayUserInfo(user: User) {
        mViewBinding.userContent.apply {
            userName.text = user.name
            userCompany.text = user.company
        }
    }

    override fun getViewBinding(): ActivityUserDetailsBinding =
        ActivityUserDetailsBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_LOGIN = "user_login"

        fun getStartIntent(
            context: Context,
            userId: Long,
            userLogin: String
        ) = Intent(context, UserDetailsActivity::class.java).apply {
            putExtra(KEY_USER_ID, userId)
            putExtra(KEY_USER_LOGIN, userLogin)
        }
    }
}
