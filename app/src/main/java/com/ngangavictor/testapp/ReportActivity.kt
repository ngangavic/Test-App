package com.ngangavictor.testapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ngangavictor.testapp.adapter.UserAdapter
import com.ngangavictor.testapp.data.User
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

class ReportActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private var userList: MutableList<User>? = null
    private var recyclerViewAdapter: UserAdapter? = null
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        queue = Volley.newRequestQueue(this)

        userList = ArrayList()
        loadData()
    }

    private fun loadData() {

        val str = object : StringRequest(
            Method.POST, Api.loadUsers,
            Response.Listener { response ->
                Log.d("USERS ACTIVITY:", response.toString())

                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {

                    //getting product object from json array
                    val product = jsonArray.getJSONObject(i)

                    //adding the product to product list
                    val user = User(
                        product.getString("email"),
                        product.getString("phone"),
                        product.getString("date")
                    )
                    userList?.add(user)

                }
                recyclerViewAdapter = UserAdapter(
                    applicationContext,
                    userList as ArrayList<User>
                )
                recyclerView.adapter = recyclerViewAdapter
            },
            Response.ErrorListener { error ->
                //                Snackbar.make(fragment_catalog, "An error occurred", Snackbar.LENGTH_LONG).show()
                error.printStackTrace()
                Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()

            })
        {
//            override fun getParams(): Map<String, String> {
//                val param = HashMap<String, String>()
//                param["uid"] = uid
//                return param
//            }
        }
        str.retryPolicy = DefaultRetryPolicy(
            7000,
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(str)
    }

}
