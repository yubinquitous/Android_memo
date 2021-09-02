package com.yubin.memo60.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yubin.memo60.BaseFragment

class MainPagerAdapter (fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragments: ArrayList<BaseFragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: BaseFragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }

    @ExperimentalStdlibApi
    fun removeFragment(){
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }
}