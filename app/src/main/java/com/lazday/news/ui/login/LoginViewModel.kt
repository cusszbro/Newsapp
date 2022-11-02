package com.lazday.news.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lazday.news.source.data.Profile
import com.lazday.news.source.data.ProfileRepository

class LoginViewModel(application: Application) : ViewModel() {
    private val mProfilRepository: ProfileRepository = ProfileRepository(application)

    fun getLogin(username: String, password:String):LiveData<Profile> = mProfilRepository.getProfileById(username,password)
}