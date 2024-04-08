package com.dicoding.githubsubmissionbaru.data.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsubmissionbaru.R
import com.dicoding.githubsubmissionbaru.data.adapter.UserAdapter
import com.dicoding.githubsubmissionbaru.data.response.ItemsItem
import com.dicoding.githubsubmissionbaru.data.response.ResponseGithub
import com.dicoding.githubsubmissionbaru.data.retrofit.ApiConfig
import com.dicoding.githubsubmissionbaru.data.setting.SettingThemes
import com.dicoding.githubsubmissionbaru.data.viewmodel.FactoryView
import com.dicoding.githubsubmissionbaru.data.viewmodel.MainViewModell
import com.dicoding.githubsubmissionbaru.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myTag = "Destroy"

    companion object {
        private const val TAG = "MainActivity"
    }

    private val mainViewModel : MainViewModell by viewModels { FactoryView.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }

        mainViewModel.listUser.observe(this) { user ->
            setUserData(user!!)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val query: CharSequence
                    query = searchView.text
                    searchView.hide()
                    resultUser(query.toString())
                    false
                }
            searchBar.inflateMenu(R.menu.listmenu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId){
                    R.id.item1 -> {
                        val intent = Intent(this@MainActivity, FavoritActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.item2 -> {
                        val intent = Intent(this@MainActivity, SettingThemes::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserList.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        Log.i(myTag, "onCreate")
    }

    private fun resultUser(query: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<ResponseGithub> {
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responseBody.items?.let { setUserData(it) }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(userList: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userList)
        binding.rvUserList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }
}