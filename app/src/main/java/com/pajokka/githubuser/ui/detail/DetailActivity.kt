package com.pajokka.githubuser.ui.detail

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.pajokka.githubuser.R
import com.pajokka.githubuser.data.source.local.entity.UserModel
import com.pajokka.githubuser.databinding.ActivityDetailBinding
import com.pajokka.githubuser.utils.Preferences
import com.pajokka.githubuser.utils.ViewModelFactory
import com.pajokka.githubuser.utils.ZoomOutPageTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailActivity : AppCompatActivity(), KodeinAware {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dataUser: UserModel
    private lateinit var detailViewModel: DetailViewModel
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()

    companion object {
        const val DATA_USER = "DATA_USER"
        const val USERNAME_USER = "USERNAME_USER"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel = obtainViewModel(this@DetailActivity)
        binding.ivArrowBack.setOnClickListener {
            finish()
        }

        dataUser = intent.getParcelableExtra<UserModel>(DATA_USER) as UserModel
        setDetailUser(dataUser)
        setDataFavorite(
            dataUser.username,
            dataUser.name,
            dataUser.avatar,
            dataUser.company,
            dataUser.location,
            dataUser.repository,
            dataUser.follower,
            dataUser.following
        )
        setTabLayout()

        val usernamePref = Preferences(this)
        usernamePref.setValues(USERNAME_USER, dataUser.username)

        val checkDarkMode =
            this.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
        if (checkDarkMode == Configuration.UI_MODE_NIGHT_YES) {
            binding.ivArrowBack.setImageResource(R.drawable.ic_arrow_back_white)
            binding.ivSetting.setImageResource(R.drawable.ic_dots_white)
        }
    }

    private fun setTabLayout() {
        val viewPager = binding.viewPager

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        val tabs = binding.tabs.getChildAt(0) as ViewGroup

        val tab = tabs.getChildAt(0)
        val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
        layoutParams.marginEnd = 100
        tab.layoutParams = layoutParams

        binding.tabs.requestLayout()
    }

    private fun setDetailUser(dataUser: UserModel) {
        with(binding) {
            tvName.text = dataUser.name
            tvUsername.text = dataUser.username
            tvCompany.text = dataUser.company
            tvLocation.text = dataUser.location
            tvRepository.text = dataUser.repository
            tvFollowers.text = dataUser.follower
            tvFollowing.text = dataUser.following

            Glide.with(this@DetailActivity)
                .load(dataUser.avatar)
                .into(ivProfile)

            ivShare.setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, """
                    Name : ${dataUser.name}
                    Company : ${dataUser.company}
                    Location: ${dataUser.location}
                    Repository : ${dataUser.repository}
                """.trimIndent()
                    )
                    type = "text/plain"
                }

                startActivity(sendIntent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setButtonFavorite(favorite: Boolean) {
        val btnOutline = ContextCompat.getDrawable(this, R.drawable.shape_retangle_button_followed)
        val btnSolid = ContextCompat.getDrawable(this, R.drawable.shape_retangle_button)

        val txtFollow = resources.getString(R.string.follow)
        val txtFollowed = resources.getString(R.string.followed)

        val colorPurple = ContextCompat.getColor(this, R.color.purple_500)
        val colorWhite = ContextCompat.getColor(this, R.color.white)

        if (favorite) {
            binding.btnFav.background = btnOutline
            binding.btnFav.text = txtFollowed
            binding.btnFav.setTextColor(colorPurple)
        } else {
            binding.btnFav.background = btnSolid
            binding.btnFav.text = txtFollow
            binding.btnFav.setTextColor(colorWhite)
        }
    }

    private fun setDataFavorite(
        username: String,
        name: String,
        avatar: String,
        company: String,
        location: String,
        repository: String,
        follower: String,
        following: String,
    ) {
        var isFavorite = false

        CoroutineScope(Dispatchers.IO).launch {
            val checkUser = detailViewModel.getUserByUsername(username)

            withContext(Dispatchers.Main) {
                when {
                    checkUser != null -> isFavorite = true
                    else -> isFavorite = false
                }

                setButtonFavorite(isFavorite)
            }
        }

        binding.btnFav.setOnClickListener {
            isFavorite = !isFavorite

            CoroutineScope(Dispatchers.IO).launch {
                if (isFavorite) {
                    detailViewModel.addToFavorite(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        follower,
                        following
                    )
                } else {
                    detailViewModel.deleteFavorite(username)
                }
            }

            setButtonFavorite(isFavorite)

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }
}