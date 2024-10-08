package com.github.wjlee611.w02activityfragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.github.wjlee611.w02activityfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 스플래시 화면 적용
        installSplashScreen()
        // 바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 바텀 네비 및 프래그먼트 초기화
        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        val fragContainerId = binding.fragmentContainer.id

        supportFragmentManager.beginTransaction()
            .replace(fragContainerId, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    changeFragment(HomeFragment())
                    true
                }
                R.id.lookFragment -> {
                    changeFragment(LookFragment())
                    true
                }
                R.id.searchFragment -> {
                    changeFragment(SearchFragment())
                    true
                }
                R.id.lockerFragment -> {
                    changeFragment(LockerFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
            )
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}