package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.data.entities.User
import com.example.flo.data.remote.AuthService
import com.example.flo.data.remote.Result
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val password: String = binding.loginPasswordEt.text.toString()

//        val songDB = SongDatabase.getInstance(this)!!
//        val user = songDB.userDao().getUser(email, password)
//
//        user?.let {
//            Log.d("LOGIN", "userId: ${user.id}, ${user}")
//            saveJWT(user.id)
//            startMainActivity()
//            return
//        }
//
//        Toast.makeText(this, "회원정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email, password, "ASD"))
    }

    private fun saveJWT(jwt: Int) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun saveJWT2(jwt: String) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when(code) {
            1000 -> {
                saveJWT2(result.jwt)
                startMainActivity()
            }
        }
    }

    override fun onLoginError(msg: String) {
        Log.d("LOGIN/ERR", msg)
    }
}