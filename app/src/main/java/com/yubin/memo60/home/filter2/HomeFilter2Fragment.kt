package com.yubin.memo60.home.filter2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.yubin.memo60.BaseFragment
import com.yubin.memo60.databinding.FragmentHomeFilter2Binding
import com.yubin.memo60.db_memo.Memo
import com.yubin.memo60.db_memo.MemoDB
import com.yubin.memo60.memoview.MemoViewActivity

class HomeFilter2Fragment: BaseFragment() {
    private lateinit var binding: FragmentHomeFilter2Binding
    private lateinit var rvAdapter0: HomeFilter2RvAdapter
    private lateinit var rvAdapter1: HomeFilter2RvAdapter
    private lateinit var rvAdapter2: HomeFilter2RvAdapter
    private lateinit var rvAdapter3: HomeFilter2RvAdapter
    private lateinit var rvAdapter4: HomeFilter2RvAdapter
    private lateinit var rvAdapter5: HomeFilter2RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeFilter2Binding.inflate(layoutInflater)

        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getWriterIdx()
        if (token != 0){
            getMemos(token)
        }
    }

    private fun getWriterIdx(): Int{
        val spf : SharedPreferences = requireActivity().getSharedPreferences("memoapp2", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token", 0)
    }

    private fun initRecyclerView(){
        val lm0 = LinearLayoutManager(requireActivity())
        val lm1 = LinearLayoutManager(requireActivity())
        val lm2 = LinearLayoutManager(requireActivity())
        val lm3 = LinearLayoutManager(requireActivity())
        val lm4 = LinearLayoutManager(requireActivity())
        val lm5 = LinearLayoutManager(requireActivity())
        lm0.orientation = LinearLayoutManager.HORIZONTAL
        lm1.orientation = LinearLayoutManager.HORIZONTAL
        lm2.orientation = LinearLayoutManager.HORIZONTAL
        lm3.orientation = LinearLayoutManager.HORIZONTAL
        lm4.orientation = LinearLayoutManager.HORIZONTAL
        lm5.orientation = LinearLayoutManager.HORIZONTAL
        binding.homeFilter2St0Rv.layoutManager = lm0
        binding.homeFilter2St1Rv.layoutManager = lm1
        binding.homeFilter2St2Rv.layoutManager = lm2
        binding.homeFilter2St3Rv.layoutManager = lm3
        binding.homeFilter2St4Rv.layoutManager = lm4
        binding.homeFilter2St5Rv.layoutManager = lm5

        rvAdapter0 = HomeFilter2RvAdapter(requireActivity())
        rvAdapter1 = HomeFilter2RvAdapter(requireActivity())
        rvAdapter2 = HomeFilter2RvAdapter(requireActivity())
        rvAdapter3 = HomeFilter2RvAdapter(requireActivity())
        rvAdapter4 = HomeFilter2RvAdapter(requireActivity())
        rvAdapter5 = HomeFilter2RvAdapter(requireActivity())

        rvAdapter0.setItemClickListener(object : HomeFilter2RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) { startMemoViewActivity(memo) }
        })
        rvAdapter1.setItemClickListener(object : HomeFilter2RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) { startMemoViewActivity(memo) }
        })
        rvAdapter2.setItemClickListener(object : HomeFilter2RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) { startMemoViewActivity(memo) }
        })
        rvAdapter3.setItemClickListener(object : HomeFilter2RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) { startMemoViewActivity(memo) }
        })
        rvAdapter4.setItemClickListener(object : HomeFilter2RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) { startMemoViewActivity(memo) }
        })
        rvAdapter5.setItemClickListener(object : HomeFilter2RvAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, memo: Memo) { startMemoViewActivity(memo) }
        })

        binding.homeFilter2St0Rv.adapter = rvAdapter0
        binding.homeFilter2St1Rv.adapter = rvAdapter1
        binding.homeFilter2St2Rv.adapter = rvAdapter2
        binding.homeFilter2St3Rv.adapter = rvAdapter3
        binding.homeFilter2St4Rv.adapter = rvAdapter4
        binding.homeFilter2St5Rv.adapter = rvAdapter5
    }

    private fun getMemos(writer: Int){
        val db: MemoDB = Room.databaseBuilder(requireActivity(), MemoDB::class.java,"memo2-db").allowMainThreadQueries().build()
        val list0 = ArrayList(db.memoDao().getMemosBySticker(0, writer))
        val list1 = ArrayList(db.memoDao().getMemosBySticker(1, writer))
        val list2 = ArrayList(db.memoDao().getMemosBySticker(2, writer))
        val list3 = ArrayList(db.memoDao().getMemosBySticker(3, writer))
        val list4 = ArrayList(db.memoDao().getMemosBySticker(4, writer))
        val list5 = ArrayList(db.memoDao().getMemosBySticker(5, writer))
//        for (_memo in list){
//            Log.d("filter2-memo-db", "idx: ${_memo.idx}, title: ${_memo.title}, content: ${_memo.content}, date: ${_memo.date}, sticker: ${_memo.sticker}, writer: ${_memo.writer}")
//        }

        rvAdapter0.addItems(list0)
        rvAdapter1.addItems(list1)
        rvAdapter2.addItems(list2)
        rvAdapter3.addItems(list3)
        rvAdapter4.addItems(list4)
        rvAdapter5.addItems(list5)

    }

    private fun startMemoViewActivity(memo: Memo) {
        val intent = Intent(requireActivity(), MemoViewActivity::class.java)
        val gson = Gson()
        val json = gson.toJson(memo)

        intent.putExtra("memo", json)
        startActivity(intent)

        requireActivity().finish()
    }
}