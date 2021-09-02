package com.yubin.memo60.memowrite

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.room.Room
import com.google.gson.Gson
import com.yubin.memo60.BaseActivity
import com.yubin.memo60.databinding.ActivityMemoWriteBinding
import com.yubin.memo60.db_memo.Memo
import com.yubin.memo60.db_memo.MemoDB
import com.yubin.memo60.main.MainActivity
import io.reactivex.internal.subscriptions.SubscriptionHelper.cancel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timer

class MemoWriteActivity : BaseActivity() {
    private lateinit var binding: ActivityMemoWriteBinding
    private var stNum: Int = 0
//    private var updateTime: String = ""
//    private lateinit var currentTime : Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            it.getIntExtra("sticker-number", 0).let { number ->
                stNum = number
            }
            Log.d("sticker-number", "$stNum")
        }
    }

    override fun onResume() {
        super.onResume()
        var remainSec = 60

        val timerTask = timer(daemon = false, period = 1000) {
            runOnUiThread {
                binding.memoWriteTimerTv.text = remainSec.toString()
            }
            remainSec--
            if (remainSec == 0){
                write()
            }
        }
        binding.memoWriteBackIv.setOnClickListener {
            timerTask.cancel()
            startMainActivity()
        }
        binding.memoWriteSaveIv.setOnClickListener {
            timerTask.cancel()
            write()
        }
    }

    private fun write() {
        val title = binding.memoWriteTitleEt.text
        val content = binding.memoWriteContentEt.text
        val currentTime= Calendar.getInstance().time
        val updateTime =
            SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.getDefault()).format(currentTime)

        val spf: SharedPreferences = getSharedPreferences("memoapp2", MODE_PRIVATE)
        val token = spf.getInt("token", 0)
        if (token != 0) {
            val memo = Memo(
                title = title.toString(),
                content = content.toString(),
                date = updateTime,
                sticker = stNum,
                writer = token
            )
            val db: MemoDB =
                Room.databaseBuilder(this, MemoDB::class.java, "memo2-db").allowMainThreadQueries()
                    .build()
            db.memoDao().writeMemo(memo)

//            memo 잘 들어갔는지 확인용
            val memos = db.memoDao().getMemos()
            for(_memo in memos){
                Log.d("memowrite-memo-db", "idx: ${_memo.idx}, title: ${_memo.title}, content: ${_memo.content}, sticker: ${_memo.sticker}, writer: ${_memo.writer}")
            }
            startMainActivity()
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this@MemoWriteActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}