package com.github.wjlee611.w02activityfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.github.wjlee611.w02activityfragment.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeMusicLl1.setOnClickListener {
            val fragContainerId = R.id.fragment_container
            val albumFrag = AlbumFragment().apply {
                val sendBundle = Bundle().apply {
                    putString("title", binding.homeMusicTv1.text.toString())
                }
                arguments = sendBundle
            }

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                )
                .replace(fragContainerId, albumFrag)
                .commitAllowingStateLoss()
        }

        val bannerAdapter = BannerVPAdapter(this).apply {
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        }
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}