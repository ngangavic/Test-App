package com.ngangavictor.testapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.testapp.R
import com.ngangavictor.testapp.data.User
import com.ngangavictor.testapp.holder.UserHolder
import java.util.*

class UserAdapter(private var context: Context, private val users: ArrayList<User>) :
    RecyclerView.Adapter<UserHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val viewHolder: UserHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        viewHolder = UserHolder(layoutView, users)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return this.users.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.textViewEmail.text = users[position].email
        holder.textViewPhone.text = users[position].phone
        holder.textViewDate.text = users[position].date
    }


}