package com.example.virtualsportsandroid.utils.ui

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.virtualsportsandroid.R

fun AppCompatImageView.loadGameImageFromURL(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.game_image_placeholder)
        .into(this)
}