package com.yubin.memo60.splash

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import com.yubin.memo60.BaseActivity
import com.yubin.memo60.R
import com.yubin.memo60.databinding.ActivitySplashBinding
import com.yubin.memo60.main.MainActivity
import com.yubin.memo60.signin.SignInActivity
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : BaseActivity() {
    private lateinit var logoAnimation : AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        val logoIv = findViewById<ImageView>(R.id.splash_logo_iv).apply{
            setBackgroundResource(R.drawable.anim_sticker)
            logoAnimation = background as AnimationDrawable
        }
        logoAnimation.start()
    }

    override fun onResume() {
        super.onResume()

        Timer().schedule(5000) {
            autoLogin()
        }
    }

    private fun autoLogin(){
        val spf : SharedPreferences = getSharedPreferences("memoapp2", MODE_PRIVATE)
        val token = spf.getInt("token", 0)

        Log.d("splash-token", "$token")
        if (token!=0){
            startMainActivity()
            return
        }else{
            startSignInActivity()
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun startSignInActivity(){
        val intent = Intent(this@SplashActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}