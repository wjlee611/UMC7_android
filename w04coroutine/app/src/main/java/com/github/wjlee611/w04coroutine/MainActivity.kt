package com.github.wjlee611.w04coroutine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.wjlee611.w04coroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isStart: Boolean = false
    private var timerValueMills: Float = 0F
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        timer = Timer()
        timer.start()

        binding.startPauseBtn.setOnClickListener {
            when (isStart) {
                true -> {
                    isStart = false
                    binding.startPauseBtn.text = "Start"
                }
                else -> {
                    isStart = true
                    binding.startPauseBtn.text = "Pause"
                }
            }
        }

        binding.clearBtn.setOnClickListener {
            binding.startPauseBtn.text = "Start"
            binding.timerTv.text = "0:00.00"
            timerValueMills = 0F
        }
    }

    inner class Timer() : Thread() {
        override fun run() {
            super.run()

            while(true) {
                if (isStart) {
                    sleep(10)
                    timerValueMills += 10

                    runOnUiThread {
                        val mills = timerValueMills % 1000
                        val seconds = timerValueMills / 1000
                        val minutes = seconds / 60
                        binding.timerTv.text = "${minutes.toInt()}:${seconds.toInt().toString().padStart(2, '0')}.${(mills/10).toInt().toString().padStart(2, '0')}"
                    }
                }
            }
        }
    }
}