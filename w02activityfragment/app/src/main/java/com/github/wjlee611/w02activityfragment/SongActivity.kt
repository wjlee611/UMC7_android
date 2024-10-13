package com.github.wjlee611.w02activityfragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.wjlee611.w02activityfragment.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding

    private var isPause = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setBackgroundColor(getResources().getColor(R.color.white, theme));
        supportActionBar?.hide()

        val bundle = intent.extras
        val title = bundle?.getString("title") ?: ""
        val singer = bundle?.getString("singer") ?: ""

        binding.songMusicTitleTv.text = title
        binding.songSingerNameTv.text = singer

        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                val resBundle = Bundle().apply {
                    putString("title", binding.songMusicTitleTv.text.toString())
                }
                putExtras(resBundle)
            }
            setResult(Activity.RESULT_OK, intent)
            if (!isFinishing) finish()
        }

        binding.songMiniplayerIv.let { iv ->
            iv.setBackgroundResource(R.drawable.nugu_btn_play_32)
            isPause = false
            iv.setOnClickListener {
                when {
                    isPause -> {
                        iv.setBackgroundResource(R.drawable.nugu_btn_play_32)
                        isPause = false
                    }
                    else -> {
                        iv.setBackgroundResource(R.drawable.nugu_btn_pause_32)
                        isPause = true
                    }
                }
            }
        }
    }
}