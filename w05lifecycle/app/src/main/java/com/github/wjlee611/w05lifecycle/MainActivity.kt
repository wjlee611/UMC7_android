package com.github.wjlee611.w05lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.wjlee611.w05lifecycle.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var memoValue : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LC", "onCreate")
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, ConfirmActivity::class.java)
            intent.putExtra("memo", binding.memoEt.text.toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("LC", "onResume")
        if (memoValue.isEmpty()) return
        binding.memoEt.setText(memoValue)
    }

    override fun onPause() {
        super.onPause()
        Log.d("LC", "onPause")
        memoValue = binding.memoEt.text.toString()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LC", "onRestart")
        AlertDialog.Builder(this)
            .setMessage("다시 작성하시겠습니까?")
            .setNegativeButton("아니오") { dialog, which ->
                memoValue = ""
            }
            .setPositiveButton("네") {dialog, which ->

            }
            .show()
    }
}