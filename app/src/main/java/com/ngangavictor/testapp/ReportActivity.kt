package com.ngangavictor.testapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import java.util.*

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

            }) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.report_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_download -> {
//                DownloadTask(this, Api.reportUrl)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.reportUrl))
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestPermissions(): Boolean {
        val writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissionsNeeded = ArrayList<String>()

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                200
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            200 -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                    PackageManager.PERMISSION_GRANTED

                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED) {

                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        ) {
                            showDialogOK("Service Permissions are required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> requestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE ->
                                            finish()
                                    }
                                })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                        }
                    }
                }
            }
        }
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
            .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:com.ngangavictor.testapp")
                    )
                )
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

}
