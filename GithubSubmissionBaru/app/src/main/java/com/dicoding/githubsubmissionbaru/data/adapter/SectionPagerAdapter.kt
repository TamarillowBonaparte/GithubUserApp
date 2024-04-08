package com.dicoding.githubsubmissionbaru.data.adapter


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubsubmissionbaru.data.fragment.FollowFragment
class SectionPagerAdapter (activity: AppCompatActivity, val username: String): FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.ARG_USERNAME, username)
            putInt(FollowFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }

    override fun getItemCount() = 2
}
