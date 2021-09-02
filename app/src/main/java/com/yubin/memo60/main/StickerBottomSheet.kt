package com.yubin.memo60.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yubin.memo60.databinding.FragmentMemoPopBinding
import com.yubin.memo60.memowrite.MemoWriteActivity

class StickerBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMemoPopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemoPopBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.memopopSt0Iv.setOnClickListener{ stClick(0) }
        binding.memopopSt1Iv.setOnClickListener{ stClick(1) }
        binding.memopopSt2Iv.setOnClickListener{ stClick(2) }
        binding.memopopSt3Iv.setOnClickListener{ stClick(3) }
        binding.memopopSt4Iv.setOnClickListener{ stClick(4) }
        binding.memopopSt5Iv.setOnClickListener{ stClick(5) }

    }

    private fun stClick(stNum : Int){
        //스티커 정보 저장
        val intent = Intent(requireActivity(), MemoWriteActivity::class.java)
        intent.putExtra("sticker-number", stNum)
        //화면 전환
        startActivity(intent)
        requireActivity().finish()
    }
}