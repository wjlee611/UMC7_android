package com.github.wjlee611.w02activityfragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.wjlee611.w02activityfragment.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding

    private lateinit var song: Song
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setBackgroundColor(getResources().getColor(R.color.white, theme));
        supportActionBar?.hide()

        initSong()
        setPlayer(song)

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
            iv.setOnClickListener {
                song.isPlaying = !song.isPlaying
                setPlayerStatus(song.isPlaying)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong() {
        val bundle = intent.extras
        song = Song(
            bundle?.getString("title") ?: "",
            bundle?.getString("singer") ?: "",
            bundle?.getInt("second") ?: 0,
            bundle?.getInt("playTime") ?: 60,
            bundle?.getBoolean("isPlaying") ?: false,
        )
        startTimer()
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        timer.isPlaying = isPlaying

        binding.songMiniplayerIv.let { iv ->
            when {
                isPlaying -> {
                    iv.setBackgroundResource(R.drawable.nugu_btn_pause_32)
                }
                else -> {
                    iv.setBackgroundResource(R.drawable.nugu_btn_play_32)
                }
            }
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean) : Thread() {
        private var second : Int = 0
        private var mills: Float = 0F

        override fun run() {
            super.run()

            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        if (mills % 1000 == 0F) {
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "스레드 다운")
            }
        }
    }
}