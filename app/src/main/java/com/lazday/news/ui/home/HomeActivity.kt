package com.lazday.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lazday.news.R
import com.lazday.news.databinding.ActivityHomeBinding
import com.lazday.news.databinding.CustomToolbarBinding
import com.lazday.news.ui.login.LoginActivity
import com.lazday.news.util.SessionManager

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var toolbar : CustomToolbarBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = binding.toolbar
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAccessToken()
        if (token == null){
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

    }


}