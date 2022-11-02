package com.lazday.news.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import com.lazday.news.source.data.Profile
import com.lazday.news.source.data.ProfileRepository

class RegisterViewModel(application: Application):ViewModel() {
    private val mProfilRepository:ProfileRepository = ProfileRepository(application)

    fun insert(profile: Profile){
        mProfilRepository.insert(profile)
    }
}