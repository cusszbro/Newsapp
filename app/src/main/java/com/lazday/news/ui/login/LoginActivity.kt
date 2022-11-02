package com.lazday.news.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lazday.news.databinding.ActivityLoginBinding
import com.lazday.news.source.data.Profile
import com.lazday.news.ui.home.HomeActivity
import com.lazday.news.ui.register.RegisterActivity
import com.lazday.news.util.SessionManager
import com.lazday.news.util.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= obtainViewModel(this)
        sessionManager = SessionManager(this)

        binding.bLogin.setOnClickListener {
            val username= binding.edtEmail.text.toString()
            val password= binding.edtPassword.text.toString()
            viewModel.getLogin(username,password).observe(this){
                goHomeActivity(it,username,password)

            }
        }
        binding.txtSignUp.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goHomeActivity(data: Profile?,username: String,password:String) {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (username== data?.username && password==data.password){
            val intent= Intent(this, HomeActivity::class.java)
            binding.progressBar.visibility=View.VISIBLE
            sessionManager.saveAccessToken(username)
            startActivity(intent)
            finish()
        }
        else if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(this,"Masukan email dan kata sandi anda",Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty()){
            Toast.makeText(this,"Masukan email terlebih dahulu",Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(this,"Masukan kata sandi terlebih dahulu",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"email atau kata sandi salah",Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtainViewModel(activity: LoginActivity): LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(LoginViewModel::class.java)
    }
}