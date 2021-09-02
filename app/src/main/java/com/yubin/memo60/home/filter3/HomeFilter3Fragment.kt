package com.yubin.memo60.home.filter3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.yubin.memo60.BaseFragment
import com.yubin.memo60.databinding.FragmentHomeFilter3Binding
import com.yubin.memo60.db_memo.Memo
import com.yubin.memo60.db_memo.MemoDB
import com.yubin.memo60.home.filter1.HomeFilter1RvAdapter
import com.yubin.memo60.memoview.MemoViewActivity

class HomeFilter3Fragment: BaseFragment() {
    private lateinit var binding: FragmentHomeFilter3Binding
    private lateinit var rvAdapter: HomeFilter1RvAdapter
    private var token : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFilter3Binding.inflate(layoutInflater)
        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = getWriterIdx()
        if (token!=0){
            getMemos(token)
        }
    }

    private fun initRecyclerView(){
        val lm = LinearLayoutManager(requireActivity())
        binding.homeFilter3Recyclerview.layoutManager = lm

        rvAdapter = HomeFilter1RvAdapter(requireActivity())

        rvAdapter.setItemClickListener(object: HomeFilter1RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) {
                startMemoViewActivity(memo)
            }
        })

        binding.homeFilter3Recyclerview.adapter = rvAdapter
    }

    private fun getMemos(writer: Int){
        val db: MemoDB = Room.databaseBuilder(requireActivity(), MemoDB::class.java, "memo2-db").allowMainThreadQueries().build()
        val list = ArrayList(db.memoDao().getMemosByWriter(writer))
        for (_memo in list){
            Log.d("filter3-memo-db", "idx: ${_memo.idx}, title: ${_memo.title}, content: ${_memo.content}, date: ${_memo.date}, sticker: ${_memo.sticker}, writer: ${_memo.writer}")
        }
        rvAdapter.addItems(list)
    }

    private fun getWriterIdx(): Int{
        val spf: SharedPreferences = requireActivity().getSharedPreferences("memoapp2", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token", 0)
    }

    private fun startMemoViewActivity(memo: Memo){
        val intent = Intent(requireActivity(), MemoViewActivity::class.java)
        val gson = Gson()
        val json = gson.toJson(memo)

        intent.putExtra("memo", json)
        startActivity(intent)
        requireActivity().finish()
    }
}