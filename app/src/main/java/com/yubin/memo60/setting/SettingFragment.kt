package com.yubin.memo60.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.room.Room
import com.yubin.memo60.BaseFragment
import com.yubin.memo60.R
import com.yubin.memo60.databinding.FragmentSettingBinding
import com.yubin.memo60.db_user.User
import com.yubin.memo60.db_user.UserDB
import com.yubin.memo60.signin.SignInActivity

class SettingFragment: BaseFragment() {
    private lateinit var binding: FragmentSettingBinding
    private var idx : Int = 0
    private var stNum : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)

        idx = getUserIdx()
        val userDB : UserDB = Room.databaseBuilder(requireActivity(), UserDB::class.java, "user2-db").allowMainThreadQueries().build()
        val user = userDB.userDao().getUserByIdx(idx)
        if (user != null) {
            setProfile(user)
        }

        binding.settingNameEt.visibility = GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingEditBt.setOnClickListener{
            editProfile()
        }
        binding.settingLogoutBt.setOnClickListener {
            logout()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun editProfile(){
        val saveProfileText : String = "프로필 저장"
        val editProfileText : String = "프로필 수정"
        binding.settingStickerIv.setOnClickListener{
            binding.settingStickerCl.visibility = VISIBLE
            binding.settingEditBt.visibility = INVISIBLE
            binding.settingLogoutBt.visibility = INVISIBLE
            binding.settingStickerCl.bringToFront()
            binding.settingStickerSt0Iv.setOnClickListener { editSticker(0) }
            binding.settingStickerSt1Iv.setOnClickListener { editSticker(1) }
            binding.settingStickerSt2Iv.setOnClickListener { editSticker(2) }
            binding.settingStickerSt3Iv.setOnClickListener { editSticker(3) }
            binding.settingStickerSt4Iv.setOnClickListener { editSticker(4) }
            binding.settingStickerSt5Iv.setOnClickListener { editSticker(5) }
        }

        if (binding.settingStickerCl.visibility == GONE) {
            if (binding.settingNameEt.visibility == GONE) { //이름 수정
                binding.settingNameEt.setText(binding.settingNameTv.text)
                binding.settingNameTv.visibility = GONE
                binding.settingNameEt.visibility = VISIBLE
                binding.settingNameEt.bringToFront()
                binding.settingEditBt.text = saveProfileText
            } else { //이름 저장
                val name = binding.settingNameEt.text.toString()
                val userDB: UserDB =
                    Room.databaseBuilder(requireActivity(), UserDB::class.java, "user2-db")
                        .allowMainThreadQueries().build()
                userDB.userDao().updateNameByIdx(name, idx)

                binding.settingNameTv.text = binding.settingNameEt.text.toString()
                binding.settingNameEt.visibility = GONE
                binding.settingNameTv.visibility = VISIBLE
                binding.settingNameTv.bringToFront()
                binding.settingEditBt.text = editProfileText
            }
        }
    }

    private fun editSticker(st : Int){
        stNum = st
        val userDB: UserDB =
            Room.databaseBuilder(requireActivity(), UserDB::class.java, "user2-db")
                .allowMainThreadQueries().build()
        userDB.userDao().updateStickerByIdx(stNum, idx)
        setSticker(stNum)
        binding.settingStickerCl.visibility = GONE
        binding.settingEditBt.visibility = VISIBLE
        binding.settingLogoutBt.visibility = VISIBLE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setSticker(st : Int){
        when(st){
            0 -> binding.settingStickerIv.setImageDrawable(getDrawable(requireContext(), R.drawable.st0))
            1 -> binding.settingStickerIv.setImageDrawable(getDrawable(requireContext(), R.drawable.st1))
            2 -> binding.settingStickerIv.setImageDrawable(getDrawable(requireContext(), R.drawable.st2))
            3 -> binding.settingStickerIv.setImageDrawable(getDrawable(requireContext(), R.drawable.st3))
            4 -> binding.settingStickerIv.setImageDrawable(getDrawable(requireContext(), R.drawable.st4))
            5 -> binding.settingStickerIv.setImageDrawable(getDrawable(requireContext(), R.drawable.st5))
        }
    }
    private fun setProfile(user: User){
        stNum = user.stNum
        setSticker(stNum)
        binding.settingIdTv.text = user.id
        binding.settingNameTv.text = user.name
    }

    private fun getUserIdx(): Int{
        val spf : SharedPreferences = requireActivity().getSharedPreferences("memoapp2", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token", 0)
    }

    //logout 기능
    private fun logout(){
        val spf : SharedPreferences = requireActivity().getSharedPreferences("memoapp2",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = spf.edit()
        editor.remove("token")
        editor.apply()
        startSignInActivity()
    }

    private fun startSignInActivity(){
        val intent = Intent(requireActivity(), SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
        requireActivity().finish()
    }
}