package com.github.wjlee611.w02activityfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.wjlee611.w02activityfragment.databinding.FragmentAlbumBinding
import com.github.wjlee611.w02activityfragment.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val title = arguments?.getString("title") ?: ""
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()

        binding.albumBackIv.setOnClickListener {
            val fragContainerId = R.id.fragment_container
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                )
                .replace(fragContainerId, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, pos ->
            tab.text = information[pos]
        }.attach()

        return binding.root
    }
}