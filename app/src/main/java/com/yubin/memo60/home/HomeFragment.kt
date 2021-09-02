package com.yubin.memo60.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.yubin.memo60.BaseFragment
import com.yubin.memo60.R
import com.yubin.memo60.databinding.FragmentHomeBinding
import com.yubin.memo60.home.filter1.HomeFilter1Fragment
import com.yubin.memo60.home.filter2.HomeFilter2Fragment
import com.yubin.memo60.home.filter3.HomeFilter3Fragment

class HomeFragment: BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.homeFilter1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text_selected))
        changeFilter(1)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFilterLl.visibility = GONE
        binding.homeTopbarLl.bringToFront()

        binding.homeFilterBt.setOnClickListener{
            if (binding.homeFilterLl.visibility == GONE){
//                Toast.makeText(view.context, "필터 클릭됨", Toast.LENGTH_SHORT).show()
                binding.homeFilterLl.bringToFront()
                binding.homeFilterLl.visibility = VISIBLE
            }else{
                binding.homeFilterLl.visibility = GONE
            }
        }

        binding.homeFilter1Tv.setOnClickListener{
            changeFilter(1)
            binding.homeFilter1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text_selected))
            binding.homeFilter2Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text))
            binding.homeFilter3Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text))
            binding.homeFilterLl.visibility = GONE
        }
        binding.homeFilter2Tv.setOnClickListener{
            changeFilter(2)
            binding.homeFilter1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text))
            binding.homeFilter2Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text_selected))
            binding.homeFilter3Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text))
            binding.homeFilterLl.visibility = GONE
        }
        binding.homeFilter3Tv.setOnClickListener{
            changeFilter(3)
            binding.homeFilter1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text))
            binding.homeFilter2Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text))
            binding.homeFilter3Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_filter_text_selected))
            binding.homeFilterLl.visibility = GONE
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun changeFilter(filterNum : Int) {
        when (filterNum) {
            1 -> childFragmentManager.beginTransaction()
                .replace(R.id.home_filter_layout, HomeFilter1Fragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
            2 -> childFragmentManager.beginTransaction()
                .replace(R.id.home_filter_layout, HomeFilter2Fragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
            3 -> childFragmentManager.beginTransaction()
                .replace(R.id.home_filter_layout, HomeFilter3Fragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
        }
    }
}