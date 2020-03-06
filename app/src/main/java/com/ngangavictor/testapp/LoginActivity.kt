package com.ngangavictor.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {

    lateinit var buttonSignUp: Button
    lateinit var buttonSignIn: Button
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        queue = Volley.newRequestQueue(this)

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
            signInRequest(uname, pass)
        }

    }

    private fun signInRequest(uname: String, pass: String) {
        queue = Volley.newRequestQueue(applicationContext)

        val str = object : StringRequest(
            Method.POST, Api.signIn,
            Response.Listener { response ->
                Log.d("LOGINACTIVITY: ", response.toString())
                val obj = JSONObject(response)

                if (obj.getString("report") == "0") {
                    startActivity(Intent(applicationContext, UserActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Error! Try again.", Toast.LENGTH_SHORT).show()
                }

            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }) {
            override fun getParams(): Map<String, String> {
                val param = HashMap<String, String>()
                param["email_phone"] = uname
                param["password"] = pass
                return param
            }
        }
        str.retryPolicy = DefaultRetryPolicy(
            10000,
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(str)
    }

}
