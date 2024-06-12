package com.example.ticket_test

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment

class MainActivity : AppCompatActivity() {
    private lateinit var user_person:ImageButton

    val positiveButtonClickListener={
            dialog:DialogInterface,which:Int->
        System.exit(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user_person= findViewById(R.id.user)
        user_person.setOnClickListener{
            openDialog(Gravity.CENTER)
        }

    }
    fun openDialog(gravity: Int){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog)

        val window = dialog.window
        if (window == null)
            return
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAttributes:WindowManager.LayoutParams = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        val account:EditText = dialog.findViewById(R.id.account)
        val password:EditText = dialog.findViewById(R.id.password)
        val cancel:Button = dialog.findViewById(R.id.cancel)
        val login:Button = dialog.findViewById(R.id.login)
        val go_register:Button = dialog.findViewById(R.id.to_register)
        cancel.setOnClickListener{
            dialog.dismiss()
        }
        login.setOnClickListener{
            Toast.makeText(this,"你好像還沒有註冊喔!",Toast.LENGTH_SHORT).show()
        }
        go_register.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
            finish()
        }
        dialog.setCancelable(false)
     dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            ConfirmExit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    fun ConfirmExit(){
        val alterDialog = AlertDialog.Builder(this)
        alterDialog.setMessage("是否離開APP呢??")
        alterDialog.setPositiveButton("是",positiveButtonClickListener)
        alterDialog.setNegativeButton("否",null)
        alterDialog.setCancelable(false)
        alterDialog.show()
    }

}