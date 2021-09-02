package com.yubin.memo60.db_memo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1)
abstract class MemoDB : RoomDatabase(){
    abstract fun memoDao(): MemoDao
}
