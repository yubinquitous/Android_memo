package com.yubin.memo60.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import com.yubin.memo60.BaseActivity
import com.yubin.memo60.R
import com.yubin.memo60.databinding.ActivityMainBinding
import com.yubin.memo60.databinding.ActivitySignupBinding
import com.yubin.memo60.db_user.User
import com.yubin.memo60.db_user.UserDB

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignupBinding
    var pwchecking: Boolean = false
    private var stCount: Int = 0
    private var stNum: Int = 0
//    private lateinit var iv : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idEt = binding.signupIdTil.editText
        val pwEt = binding.signupPwTil.editText
        val pwcheckEt = binding.signupPwcheckTil.editText
        val nameEt = binding.signupNicknameTil.editText

        pwcheckEt?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (pwcheckEt.text.toString() != pwEt?.text.toString()) {
                    binding.signupPwcheckTil.error = getString(R.string.signup_pwcheck_error)
                    pwchecking = false
                } else {
                    binding.signupPwcheckTil.error = null
                    pwchecking = true
                }
            }
        })

        binding.signupSt0Iv.setOnClickListener { clickSticker(0, binding.signupSt0SelectedIv) }
        binding.signupSt1Iv.setOnClickListener { clickSticker(1, binding.signupSt1SelectedIv) }
        binding.signupSt2Iv.setOnClickListener { clickSticker(2, binding.signupSt2SelectedIv) }
        binding.signupSt3Iv.setOnClickListener { clickSticker(3, binding.signupSt3SelectedIv) }
        binding.signupSt4Iv.setOnClickListener { clickSticker(4, binding.signupSt4SelectedIv) }
        binding.signupSt5Iv.setOnClickListener { clickSticker(5, binding.signupSt5SelectedIv) }

        binding.signupSignupBt.setOnClickListener {
            signUp(
                idEt?.text.toString(),
                pwEt?.text.toString(),
                nameEt?.text.toString(),
                pwchecking,
                stCount,
                stNum
            )
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun clickSticker(st: Int, iv: ImageView) {
        stNum = st
        if (iv.visibility == VISIBLE) {
            iv.visibility = GONE
            stCount--
//            Toast.makeText(this, "stCount : ${stCount}", LENGTH_SHORT).show()
        }else{
            iv.visibility = VISIBLE
            stCount++
//            Toast.makeText(this, "stCount : ${stCount}", LENGTH_SHORT).show()
        }
    }

    private fun signUp(
        id: String,
        pw: String,
        name: String,
        pwchecking: Boolean,
        stCount: Int,
        stNum: Int
    ) {
        if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || !pwchecking || stCount != 1) {
            Toast.makeText(this, "필수 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        val userDB: UserDB =
            Room.databaseBuilder(this, UserDB::class.java, "user2-db").allowMainThreadQueries()
                .build()
        val alreadyUser = userDB.userDao().getUserById(id)

        if (alreadyUser === null) {
            val user = User(id = id, pw = pw, name = name, stNum = stNum)
            userDB.userDao().insertUser(user)
            val users = userDB.userDao().getUsers()
            for (user in users) {
                Log.d(
                    "user2-db",
                    "idx : ${user.idx}, userId : ${user.id}, userPw: ${user.pw}, userName: ${user.name}, stNum : ${user.stNum}"
                )
            }

            showDialog("가입에 성공했습니다.")
        } else {
            binding.signupIdTil.error = "이미 존재하는 회원입니다."
        }
//        for (user in users){
//            Log.d("user-db", "idx : ${user.idx}, userId : ${user.id}, userPw: ${user.pw}, userName: ${user.name}")
//        }
    }

    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }
}