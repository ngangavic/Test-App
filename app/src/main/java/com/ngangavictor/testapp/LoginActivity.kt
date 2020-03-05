package com.ngangavictor.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    lateinit var buttonSignUp:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonSignUp=findViewById(R.id.buttonSignUp)

        buttonSignUp.setOnClickListener {
            startActivity(Intent(applicationContext,RegisterActivity::class.java))
        }
    }
}
