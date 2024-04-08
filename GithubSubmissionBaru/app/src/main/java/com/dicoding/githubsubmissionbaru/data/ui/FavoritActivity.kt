package com.dicoding.githubsubmissionbaru.data.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.githubsubmissionbaru.data.adapter.FavoritAdapter
import com.dicoding.githubsubmissionbaru.data.viewmodel.FactoryView
import com.dicoding.githubsubmissionbaru.data.viewmodel.FavoritView
import com.dicoding.githubsubmissionbaru.databinding.ActivityFavoritBinding

class FavoritActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritBinding

    private val viewModel: FavoritView by viewModels { FactoryView.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        viewModel.getUsers().observe(this) {
            binding.rvUserFollowList.adapter = FavoritAdapter(it)
        }
        showLoading(false)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}