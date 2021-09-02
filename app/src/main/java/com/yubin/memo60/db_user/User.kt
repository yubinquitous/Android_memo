package com.yubin.memo60.db_user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    val id : String,
    val pw : String,
    var name : String = "",
    var stNum : Int = 0
)