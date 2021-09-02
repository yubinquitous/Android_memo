package com.yubin.memo60.memoview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.room.Room
import com.google.gson.Gson
import com.yubin.memo60.BaseActivity
import com.yubin.memo60.R
import com.yubin.memo60.databinding.ActivityMemoViewBinding
import com.yubin.memo60.db_memo.Memo
import com.yubin.memo60.db_memo.MemoDB
import com.yubin.memo60.home.filter1.HomeFilter1RvAdapter
import com.yubin.memo60.main.MainActivity

class MemoViewActivity : BaseActivity() {
    private lateinit var binding: ActivityMemoViewBinding
    private lateinit var rvAdapter: HomeFilter1RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.hasExtra("memo")
        val gson = Gson()
        val json = intent.getStringExtra("memo")
//        val pos = intent.getIntExtra("pos", 0)
        val memo = gson.fromJson(json, Memo::class.java)
        setMemo(memo)

        binding.memoViewBackIv.setOnClickListener{
            startMainActivity()
        }

        rvAdapter = HomeFilter1RvAdapter(this)
        //memo delete
        binding.memoViewDeleteBt.setOnClickListener {
            removeMemo(memo.idx)
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this@MemoViewActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
        finish()
    }

    private fun removeMemo(idx: Int) {
        val db: MemoDB = Room.databaseBuilder(this@MemoViewActivity, MemoDB::class.java, "memo2-db")
            .allowMainThreadQueries().build()
        db.memoDao().removeMemo(idx)

        startMainActivity()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setMemo(memo: Memo) {
        binding.memoViewTitleTv.text = memo.title
        binding.memoViewContentTv.text = memo.content
        binding.memoViewTimeTv.text = memo.date
        when (memo.sticker) {
            0 -> binding.memoViewStickerIv.setImageDrawable(getDrawable(R.drawable.st0))
            1 -> binding.memoViewStickerIv.setImageDrawable(getDrawable(R.drawable.st1))
            2 -> binding.memoViewStickerIv.setImageDrawable(getDrawable(R.drawable.st2))
            3 -> binding.memoViewStickerIv.setImageDrawable(getDrawable(R.drawable.st3))
            4 -> binding.memoViewStickerIv.setImageDrawable(getDrawable(R.drawable.st4))
            5 -> binding.memoViewStickerIv.setImageDrawable(getDrawable(R.drawable.st5))
        }
    }
}