package com.yubin.memo60.db_memo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoTable")
    fun getMemos(): List<Memo>

    @Query("SELECT * FROM MemoTable WHERE writer = :writer")
    fun getMemosByWriter(writer: Int): List<Memo>

    @Query("SELECT * FROM MemoTable WHERE sticker =:sticker AND writer =:writer")
    fun getMemosBySticker(sticker: Int, writer: Int): List<Memo>

    @Query("DELETE FROM MemoTable WHERE idx = :idx")
    fun removeMemo(idx: Int)

    @Query("SELECT * FROM MemoTable WHERE writer =:writer order by date desc")
    fun getMemosDescByWriter(writer: Int): List<Memo>

    @Insert
    fun writeMemo(memo: Memo)
}