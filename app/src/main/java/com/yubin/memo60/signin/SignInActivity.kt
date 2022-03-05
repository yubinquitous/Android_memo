package com.yubin.memo60.signin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.yubin.memo60.BaseActivity
import com.yubin.memo60.R
import com.yubin.memo60.db_user.UserDB
import com.yubin.memo60.main.MainActivity
import com.yubin.memo60.signup.SignUpActivity

class SignInActivity : BaseActivity() {
//    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

//        binding = ActivitySigninBinding.inflate(layoutInflater)
        val idEt = findViewById<EditText>(R.id.signin_id_et)
        val pwEt = findViewById<EditText>(R.id.signin_pw_et)
        val loginBt = findViewById<Button>(R.id.signin_login_bt)
        val signupBt = findViewById<TextView>(R.id.signin_signup_tv)

        loginBt.setOnClickListener {
            //id, pw 입력 오류
            signIn(idEt.text.toString(), pwEt.text.toString())
        }

        signupBt.setOnClickListener {
            startSignUpActivity()
        }
    }

    private fun signIn(id: String, pw: String) {
        if (id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (pw.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        val userDB: UserDB =
            Room.databaseBuilder(this, UserDB::class.java, "user2-db").allowMainThreadQueries()
                .build()
        val user = userDB.userDao().getUser(id, pw)

        Log.d("user2-db", "$user")

        if (user === null) {
            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
        } else {
            user.let {
                val spf: SharedPreferences = getSharedPreferences("memoapp2", MODE_PRIVATE)
                val editor = spf.edit()
                val token = user.idx

                editor.putInt("token", token)
                editor.apply()
                Log.d("signin2-token", "$token")
                startMainActivity()
            }
        }
    }

    private fun startSignUpActivity() {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity() {
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
        finish()
    }
}