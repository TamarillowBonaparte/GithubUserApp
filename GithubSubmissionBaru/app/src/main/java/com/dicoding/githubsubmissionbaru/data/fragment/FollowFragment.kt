package com.dicoding.githubsubmissionbaru.data.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsubmissionbaru.data.adapter.UserAdapter
import com.dicoding.githubsubmissionbaru.data.viewmodel.FollowerView
import com.dicoding.githubsubmissionbaru.data.viewmodel.FollowingView
import com.dicoding.githubsubmissionbaru.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private var position: Int? = null
    private var username: String? = null
    private lateinit var binding: FragmentFollowBinding
    private val followersViewModel by viewModels<FollowerView>()
    private val followingViewModel by viewModels<FollowingView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }


    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserFollowList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUserFollowList.addItemDecoration(itemDecoration)
    }

    private fun showFollowersData(adapter: UserAdapter) {
        with(followersViewModel) {
            if (user.value == null) {
                findFollowers(username!!)
                user.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.rvUserFollowList.adapter = adapter
                }
            }
            user.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                binding.rvUserFollowList.adapter = adapter
            }
            isloading.observe(viewLifecycleOwner) {
                showLoader(it)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val adapter = UserAdapter()

        recyclerView()

        if (position == 1) {
            showFollowersData(adapter)
        } else {
            showFollowingData(adapter)
        }
    }

    private fun showFollowingData(adapter: UserAdapter) {
        with(followingViewModel) {
            if (user.value == null) {
                findFollowing(username!!)
                user.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.rvUserFollowList.adapter = adapter
                }
            }
            user.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                binding.rvUserFollowList.adapter = adapter
            }
            isloading.observe(viewLifecycleOwner) {
                showLoader(it)
            }
        }
    }

    private fun showLoader(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUserFollowList.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUserFollowList.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}