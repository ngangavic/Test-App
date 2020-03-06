package com.ngangavictor.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap

class UserActivity : AppCompatActivity() {

    lateinit var textViewEmail:TextView
    lateinit var textViewPhone:TextView
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        textViewEmail=findViewById(R.id.textViewEmail)
        textViewPhone=findViewById(R.id.textViewPhone)
        queue = Volley.newRequestQueue(this)

    }

    private fun getData(uid:String) {
        queue = Volley.newRequestQueue(applicationContext)

        val str = object : StringRequest(
            Method.POST, Api.fetchData,
            Response.Listener { response ->
                Log.d("USERACTIVITY: ", response.toString())
                val obj = JSONObject(response)

                if (obj.getString("report") == "0") {

                } else {

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

}
