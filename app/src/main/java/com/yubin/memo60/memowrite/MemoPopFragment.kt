package com.yubin.memo60.memowrite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yubin.memo60.BaseFragment
import com.yubin.memo60.R
import com.yubin.memo60.databinding.ActivityMemoWriteBinding
import com.yubin.memo60.databinding.FragmentHomeBinding
import com.yubin.memo60.databinding.FragmentMemoPopBinding

class MemoPopFragment : BaseFragment() {
    private lateinit var binding: ActivityMemoWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityMemoWriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}