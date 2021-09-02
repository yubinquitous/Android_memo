package com.yubin.memo60.main

import android.os.Bundle
import android.view.MotionEvent
import android.view.View

import com.google.android.material.tabs.TabLayoutMediator
import com.yubin.memo60.R
import com.yubin.memo60.BaseActivity
import com.yubin.memo60.databinding.ActivityMainBinding
import com.yubin.memo60.home.HomeFragment
import com.yubin.memo60.memowrite.MemoPopFragment
import com.yubin.memo60.setting.SettingFragment

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pagerAdapter = MainPagerAdapter(this)
        pagerAdapter.addFragment(HomeFragment())
        pagerAdapter.addFragment(MemoPopFragment())
        pagerAdapter.addFragment(SettingFragment())

        //스와이프로 탭 이동 막기
        binding.mainViewpager.run{
            isUserInputEnabled = false
        }

        binding.mainViewpager.adapter = pagerAdapter

        TabLayoutMediator(binding.mainTabs, binding.mainViewpager){tab, position->
            when(position){
                0-> {
                    tab.icon = getDrawable(R.drawable.round_home_24)
                }
                1-> { tab.icon = getDrawable(R.drawable.outline_add_circle_outline_black_48)
                }
                2-> { tab.icon = getDrawable(R.drawable.round_settings_24)
                }
            }
            //스와이프 애니메이션 막기
            binding.mainViewpager.setCurrentItem(binding.mainViewpager.currentItem, false)
        }.attach()

        binding.mainMemoTv.setOnClickListener {
            //sticker fragment 올라오기
            val bottomSheet = StickerBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

}