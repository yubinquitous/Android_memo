package com.yubin.memo60.db_user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    @Query("SELECT * FROM UserTable WHERE id = :id AND pw = :pw")
    fun getUser(id: String, pw: String): User?

    @Insert
    fun insertUser(user: User)

    @Query("DELETE FROM UserTable")
    fun deleteUsers()

    @Query("SELECT * FROM UserTable WHERE id = :id")
    fun getUserById(id: String): User?

    @Query("SELECT * FROM UserTable WHERE idx = :idx")
    fun getUserByIdx(idx: Int): User?

    //username 수정
    @Query("UPDATE UserTable SET name = :name WHERE idx = :idx")
    fun updateNameByIdx(name: String, idx: Int)

    //usersticker 수정
    @Query("UPDATE UserTable SET stNum = :sticker WHERE idx = :idx")
    fun updateStickerByIdx(sticker : Int, idx: Int)
}