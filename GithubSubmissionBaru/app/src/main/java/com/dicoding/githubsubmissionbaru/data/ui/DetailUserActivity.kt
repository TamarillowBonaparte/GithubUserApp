package com.dicoding.githubsubmissionbaru.data.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.dicoding.githubsubmissionbaru.R
import com.dicoding.githubsubmissionbaru.data.adapter.SectionPagerAdapter
import com.dicoding.githubsubmissionbaru.data.local.DataUser
import com.dicoding.githubsubmissionbaru.data.response.ResponseDetailGithub
import com.dicoding.githubsubmissionbaru.data.retrofit.ApiConfig
import com.dicoding.githubsubmissionbaru.data.viewmodel.DetailViewModel
import com.dicoding.githubsubmissionbaru.data.viewmodel.FactoryView
import com.dicoding.githubsubmissionbaru.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }


    private val viewModel: DetailViewModel by viewModels { FactoryView.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        username?.let {

            val sectionsPagerAdapter = SectionPagerAdapter(this, username)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f

            showLoading(true)
            ApiConfig
                .getApiService()
                .getDetailUser(it)
                .enqueue(object : Callback<ResponseDetailGithub> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<ResponseDetailGithub>,
                        response: Response<ResponseDetailGithub>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { detailUser ->
                                binding.userName.text = detailUser.name
                                Glide.with(this@DetailUserActivity)
                                    .load(detailUser.avatarUrl)
                                    .into(binding.rvImageView)
                                binding.userFollowers.text = "${detailUser.followers} Followers"
                                binding.userFollowing.text = "${detailUser.following} Follwing"
                                binding.userLogin.text = detailUser.login
                                viewModel.getFavoritUser(detailUser.login).observe(this@DetailUserActivity){ favoritUser ->
                                    if(favoritUser == null){
                                        binding.favoriteButton.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                this@DetailUserActivity, R.drawable.cloud_item
                                            )
                                        )
                                        binding.favoriteButton.setOnClickListener {
                                            val user = DataUser(detailUser.login, detailUser.avatarUrl)
                                            viewModel.insertUser(user)
                                            Toast.makeText(this@DetailUserActivity, "Favorited!", Toast.LENGTH_SHORT).show()
                                        }
                                    }else{
                                        binding.favoriteButton.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                this@DetailUserActivity, R.drawable.cloud_merah
                                            )
                                        )
                                        binding.favoriteButton.setOnClickListener {
                                            viewModel.deleteUser(favoritUser)
                                            Toast.makeText(this@DetailUserActivity, "Unfavorite", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                            showLoading(false)
                        }
                    }

                    override fun onFailure(call: Call<ResponseDetailGithub>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}