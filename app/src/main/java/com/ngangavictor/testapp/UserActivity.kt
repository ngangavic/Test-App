package com.ngangavictor.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class UserActivity : AppCompatActivity() {

    lateinit var textViewEmail: TextView
    lateinit var textViewPhone: TextView
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPhone = findViewById(R.id.textViewPhone)
        queue = Volley.newRequestQueue(this)
        val uid = intent.getStringExtra("uid")
        getData(uid)
    }

    private fun getData(uid: String) {
        queue = Volley.newRequestQueue(applicationContext)

        val str = object : StringRequest(
            Method.POST, Api.fetchData,
            Response.Listener { response ->
                Log.d("USERACTIVITY: ", response.toString())
                val obj = JSONObject(response)

                if (obj.getString("report") == "0") {
                    textViewEmail.text = obj.getString("email")
                    textViewPhone.text = obj.getString("phone")
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                    Toast.makeText(applicationContext, "Please login again", Toast.LENGTH_LONG)
                        .show()
                }

            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }) {
            override fun getParams(): Map<String, String> {
                val param = HashMap<String, String>()
                param["uid"] = uid
                return param
            }
        }
        str.retryPolicy = DefaultRetryPolicy(
            7000,
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(str)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
                Toast.makeText(applicationContext, "Logout successful", Toast.LENGTH_SHORT).show()
            }
            R.id.action_report -> {
                startActivity(Intent(applicationContext, ReportActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
