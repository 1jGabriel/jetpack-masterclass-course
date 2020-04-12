package com.aupp.util.extensions

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aupp.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {

    val circularProgressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }

    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_error)
        .placeholder(circularProgressDrawable)
        .into(this)
}
