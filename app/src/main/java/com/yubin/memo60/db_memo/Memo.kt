package com.yubin.memo60.db_memo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.chrono.ChronoLocalDateTime

@Entity(tableName = "MemoTable")
data class Memo(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var date : String = "",
    var title: String = "",
    var content: String = "",
    var sticker: Int = 0,
    var writer: Int=0
)
