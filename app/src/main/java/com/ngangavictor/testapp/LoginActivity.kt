package com.ngangavictor.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var buttonSignUp: Button
    lateinit var buttonSignIn: Button
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)

        buttonSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()
        }

        buttonSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val uname = editTextEmail.text.toString()
        val pass = editTextPassword.text.toString()

        if (TextUtils.isEmpty(uname)) {
            editTextEmail.requestFocus()
            editTextEmail.error = "Required"
        } else if (TextUtils.isEmpty(pass)) {
            editTextPassword.requestFocus()
            editTextPassword.error = "Required"
        } else {
            Toast.makeText(applicationContext, "Sign In success", Toast.LENGTH_SHORT).show()
        }

    }
}
