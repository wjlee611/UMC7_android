package com.github.wjlee611.w02activityfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wjlee611.w02activityfragment.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    private lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        return binding.root
    }
}