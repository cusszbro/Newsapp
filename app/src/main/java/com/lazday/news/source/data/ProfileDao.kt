package com.lazday.news.source.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {

    @Query("Select * From profile where username= :username and password=:password")
    fun getProfile(username:String, password:String): LiveData<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)
}