package com.venans.githubuserrepos.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import coil.load
import com.venans.githubuserrepos.databinding.ActivityUserDetailsBinding
import com.venans.githubuserrepos.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserDetailsActivity : BaseActivity<UserDetailsViewModel, ActivityUserDetailsBinding>() {

    @Inject
    lateinit var viewModelFactory: UserDetailsViewModel.UserDetailsViewModelFactory

    override val mViewModel: UserDetailsViewModel by viewModels {
        val userId = intent.extras?.getLong(KEY_USER_ID)
            ?: throw IllegalArgumentException("`User` must be non-null")

        UserDetailsViewModel.provideFactory(viewModelFactory,  userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        initUser()
    }

    private fun initUser() {
        mViewModel.user.observe(this) { user ->
            mViewBinding.userContent.apply {
                userLogin.text = user.login
                userUrl.text = user.url
            }
            mViewBinding.userAvatarImage.load(user.avatarUrl)
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

        fun getStartIntent(
            context: Context,
            userId: Long
        ) = Intent(context, UserDetailsActivity::class.java).apply { putExtra(KEY_USER_ID, userId) }
    }
}
