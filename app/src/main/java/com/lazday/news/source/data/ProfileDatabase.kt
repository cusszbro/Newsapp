package com.lazday.news.source.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1)
abstract class ProfileDatabase: RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object{
        @Volatile
        private var INSTANCE:ProfileDatabase?=null

        fun getInstance(context: Context):ProfileDatabase{  if (INSTANCE == null) {
            synchronized(ProfileDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ProfileDatabase::class.java, "profile.db")
                    .build()
            }
        }
            return INSTANCE as ProfileDatabase
        }

    }
}
