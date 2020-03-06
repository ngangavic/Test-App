package com.ngangavictor.testapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class RegisterActivity : AppCompatActivity() {
    lateinit var buttonSignIn: Button
    lateinit var buttonSignUp: Button
    lateinit var editTextEmail: EditText
    lateinit var editTextPhone: EditText
    lateinit var queue: RequestQueue
    lateinit var alert:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        queue = Volley.newRequestQueue(this)

        buttonSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }

        buttonSignUp.setOnClickListener { signUp() }

    }

    private fun signUp() {
        val email = editTextEmail.text.toString()
        val phone = editTextPhone.text.toString()

        if (TextUtils.isEmpty(email)) {
            editTextEmail.requestFocus()
            editTextEmail.error = "Required"
        } else if (TextUtils.isEmpty(phone)) {
            editTextPhone.requestFocus()
            editTextPhone.error = "Required"
        } else {
            loadAlert()
            signUpRequest(email, phone)
        }
    }

    private fun signUpRequest(email: String, phone: String) {
        queue = Volley.newRequestQueue(applicationContext)

        val str = object : StringRequest(
            Method.POST, Api.signUp,
            Response.Listener { response ->
                Log.d("REGACTIVITY: ", response.toString())
                val obj = JSONObject(response)
alert.cancel()
                alert.dismiss()
                if (obj.getString("report") == "0") {
                    successAlert()
                } else {
                    Toast.makeText(applicationContext, "Error! Try again.", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }) {
            override fun getParams(): Map<String, String> {
                val param = HashMap<String, String>()
                param["email"] = email
                param["phone"] = phone
                return param
            }
        }
        str.retryPolicy = DefaultRetryPolicy(
            60000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(str)
    }

    private fun successAlert() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setCancelable(false)
        setTitle("Success")
        alertBuilder.setMessage("Registration successful. Check your email for the password.")
        alertBuilder.setPositiveButton("OK", { dialog, which ->
            dialog.dismiss()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        })

        val alert = alertBuilder.create()
        alert.show()
    }

    private fun loadAlert() {
        val alertBuilder = AlertDialog.Builder(this,R.style.CustomDialog)
        alertBuilder.setCancelable(false)
        setTitle("Success")
        val progress=ProgressBar(this)
        alertBuilder.setView(progress)
//        alertBuilder.setMessage("Registration successful. Check your email for the password.")
//        alertBuilder.setPositiveButton("OK", { dialog, which ->
//            dialog.dismiss()
//            startActivity(Intent(applicationContext, LoginActivity::class.java))
//            finish()
//        })

        alert = alertBuilder.create()
        alert?.show()
    }

}
