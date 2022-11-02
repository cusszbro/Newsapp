package com.lazday.news.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lazday.news.databinding.ActivityRegisterBinding
import com.lazday.news.source.data.Profile
import com.lazday.news.ui.login.LoginActivity
import com.lazday.news.util.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= obtainViewModel(this)
        binding.btnCreateAccount.setOnClickListener {
            val name= binding.edtName.text.toString()
            val email= binding.edtEmail.text.toString()
            val password= binding.edtPassword.text.toString()
            val password2= binding.edtPassword2.text.toString()
            val profile = Profile(0, email,password,name)
            when{
                name.isEmpty() && email.isEmpty() && password.isEmpty() && password2.isEmpty() ->{
                    Toast.makeText(this, "Lengkapi form terlebih dahulu",Toast.LENGTH_SHORT).show()
                }
                name.isEmpty() -> {
                    Toast.makeText(this, "Masukan nama terlebih dahulu",Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(this, "Masukan email terlebih dahulu",Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Masukan Kata sandi terlebih dahulu",Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Kata sandi kurang dari 6 karakter",Toast.LENGTH_SHORT).show()
                }
                password2.isEmpty() -> {
                    Toast.makeText(this, "Masukan Kata sandi terlebih dahulu",Toast.LENGTH_SHORT).show()
                }
                password2.length < 6 -> {
                    Toast.makeText(this, "Kata sandi kurang dari 6 karakter",Toast.LENGTH_SHORT).show()
                }
                password != password2 -> {
                    Toast.makeText(this, "Masukan Kata Sandi yang sama",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.insert(profile)
                    Toast.makeText(this, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.txtSignin.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun obtainViewModel(activity: RegisterActivity): RegisterViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(RegisterViewModel::class.java)
    }
}