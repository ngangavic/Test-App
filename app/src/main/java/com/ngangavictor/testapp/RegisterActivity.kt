package com.ngangavictor.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    lateinit var buttonSignIn:Button
    lateinit var buttonSignUp:Button
    lateinit var editTextEmail:EditText
    lateinit var editTextPhone:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonSignIn=findViewById(R.id.buttonSignIn)
        buttonSignUp=findViewById(R.id.buttonSignUp)
        editTextEmail=findViewById(R.id.editTextEmail)
        editTextPhone=findViewById(R.id.editTextPhone)

        buttonSignIn.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            finish()
        }

        buttonSignUp.setOnClickListener {signUp()  }

    }

    private fun signUp(){
        val email=editTextEmail.text.toString()
        val phone=editTextPhone.text.toString()

        if (TextUtils.isEmpty(email)){
            editTextEmail.requestFocus()
            editTextEmail.error="Required"
        }else if (TextUtils.isEmpty(phone)){
            editTextPhone.requestFocus()
            editTextPhone.error="Required"
        }else{
            Toast.makeText(applicationContext, "Sign Up success", Toast.LENGTH_SHORT).show()
        }


    }

}
