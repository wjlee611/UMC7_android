package com.example.flo.data.remote

import com.example.flo.LoginView
import com.example.flo.SignupView
import com.example.flo.data.entities.User
import com.example.flo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signupView: SignupView
    private lateinit var loginView: LoginView

    fun setSignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signup(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.signup(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val res = response.body()
                when(res?.code) {
                    1000 -> signupView.onSingupSuccess()
                    else -> signupView.onSignupError(res?.code.toString() ?: response.message())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                signupView.onSignupError(t.message.toString())
            }
        })
    }

    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val res = response.body()
                when(res?.code) {
                    1000 -> loginView.onLoginSuccess(1000, res.result!!)
                    else -> loginView.onLoginError(res?.code.toString() ?: response.message())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                loginView.onLoginError(t.message.toString())
            }
        })
    }
}