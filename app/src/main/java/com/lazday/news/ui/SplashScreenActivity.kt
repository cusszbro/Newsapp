package com.lazday.news.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.lazday.news.databinding.ActivitySplashScreenBinding
import com.lazday.news.ui.home.HomeActivity

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashTime: Long = 3000

        animation()

        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }

    private fun animation(){
        val tvNameApp = ObjectAnimator.ofFloat(binding.tvNameApp, View.ALPHA, 1f).setDuration(500)
        val tvSlogan = ObjectAnimator.ofFloat(binding.tvSlogan, View.ALPHA, 1f).setDuration(500)
        val tvSponsored = ObjectAnimator.ofFloat(binding.tvSponsored, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(tvNameApp, tvSlogan, tvSponsored)
            startDelay = 500
        }.start()
    }
}