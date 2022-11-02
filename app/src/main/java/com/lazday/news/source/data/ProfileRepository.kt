package com.lazday.news.source.data

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProfileRepository(application: Application) {
    private val mProfileDao: ProfileDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db=ProfileDatabase.getInstance(application)
        mProfileDao=db.profileDao()
    }

    fun getProfileById(username: String, password: String): LiveData<Profile> = mProfileDao.getProfile(username,password)

    fun insert(profile: Profile){
        executorService.execute { mProfileDao.insert(profile) }
    }
}