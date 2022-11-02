package com.lazday.news.source.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="profile")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String?=null,

    @ColumnInfo(name = "password")
    var password: String?=null,

    @ColumnInfo(name = "name")
    var name:String?=null
)
