package com.ngangavictor.testapp.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.testapp.R
import com.ngangavictor.testapp.data.User

class UserHolder(itemView: View, private val catalogObject: List<User>) :
    RecyclerView.ViewHolder(itemView) {

    var textViewEmail: TextView
    var textViewPhone: TextView
    var textViewDate: TextView

    init {
        textViewEmail = itemView.findViewById(R.id.textViewEmail) as TextView
        textViewPhone = itemView.findViewById(R.id.textViewPhone) as TextView
        textViewDate = itemView.findViewById(R.id.textViewDate) as TextView
    }


}