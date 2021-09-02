package com.yubin.memo60

import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity :AppCompatActivity(), View.OnClickListener, BaseDialog.BaseDialogClickListener {
    override fun onClick(v: View?) {
    }

    fun showDialog(title: String){
        val dig = BaseDialog(this)
        dig.listener = this
        dig.show(title)
    }
    override fun onOKClicked() {
    }
}