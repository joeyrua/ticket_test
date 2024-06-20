package com.example.ticket_test

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class Register : AppCompatActivity() {
    private lateinit var re_Email:TextInputLayout
    private lateinit var re_Phone:TextInputLayout
    private lateinit var re_Name:TextInputLayout
    private lateinit var re_Id:TextInputLayout
    private lateinit var re_Password:TextInputLayout
    private lateinit var register_Btn:Button
    private lateinit var back_Home:Button
    private lateinit var dbRef:DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        re_Email = findViewById(R.id.email)
        re_Phone = findViewById(R.id.phone)
        re_Name = findViewById(R.id.name)
        re_Id = findViewById(R.id.id)
        re_Password = findViewById(R.id.password)
        register_Btn = findViewById(R.id.re)
        back_Home = findViewById(R.id.to_home)

        dbRef = FirebaseDatabase.getInstance().getReference("user")


        register_Btn.setOnClickListener {
            if (!username_vaild() || !useremail_vaild() || !userid_vaild()||!userphone_vaild()||!userpassword_vaild()){
                return@setOnClickListener
            }
            var input_email = re_Email.editText?.text.toString()
            var input_phone = re_Phone.editText?.text.toString()
            var input_name = re_Name.editText?.text.toString()
            var input_id = re_Id.editText?.text.toString()
            var input_password = re_Password.editText?.text.toString()

            val user_id = dbRef.push().key!!
            val user = UserProfile(input_email, input_phone, input_name, input_id, input_password)
            dbRef.child(user_id).setValue(user)
                .addOnCompleteListener{
                    Toast.makeText(this,"Successfully!!",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
        }

        back_Home.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun username_vaild():Boolean{
        val username = re_Name.editText?.text.toString()
        if (username.isEmpty()){
            re_Name.error = "必須輸入此欄位"
            return false
        }
        else{
            re_Name.error=null
            return true
        }
    }
    private fun useremail_vaild():Boolean{
        val useremail = re_Email.editText?.text.toString()
        val useremail_pattern = Pattern.compile("[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*\$")
        val useremail_matcher = useremail_pattern.matcher(useremail)
        if(useremail.isEmpty()){
            re_Email.error="必須輸入此欄位"
            return false
        }else if (!useremail_matcher.matches()){
            re_Email.error="輸入格式錯誤"
            return false
        }
        else{
            re_Email.error=null
            return true
        }
    }
    private fun userphone_vaild():Boolean{
        val userphone = re_Phone.editText?.text.toString()
        val pattern = Pattern.compile("[0]{1}[9]{1}[0-9]{8}")
        val marcher = pattern.matcher(userphone)

        if(userphone.isEmpty()){
            re_Phone.error="必須輸入此欄位"
            return false
        }else if (userphone.length in 1..9 || !marcher.matches()){
            re_Phone.error="請輸入正確的格式"
            return false
        }
        else{
            re_Phone.error=null
            return true
        }
    }
    private fun userid_vaild():Boolean{
        val userid = re_Id.editText?.text.toString()
        val pattern_userid = Pattern.compile("[A-Z]{1}[1-2]{1}[0-9]{8}")
        val matcher_userid = pattern_userid.matcher(userid)
        if(userid.isEmpty()){
            re_Id.error="必須輸入此欄位"
            return false
        }else if (userid.length in 1..9 ||!matcher_userid.matches()){
            re_Id.error="請輸入位元數>9"
            return false
        }
        else{
            re_Id.error=null
            return true
        }
    }

    private fun userpassword_vaild():Boolean{
        val userpassword = re_Password.editText?.text.toString()
        if(userpassword.isEmpty()){
            re_Password.error="必須輸入此欄位"
            return false
        }else if (userpassword.length in 1..11){
            re_Password.error="密碼必須輸入超過12個字元"
            return false
        }
        else{
            re_Id.error=null
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}