package com.example.marvelapp.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.getImageByUrl(url: String) {
    Glide.with(context).load(url).into(this)
}
