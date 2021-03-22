package com.example.virtualsportsandroid.utils.ui

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.virtualsportsandroid.R

fun AppCompatImageView.loadImageFromURL(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun AppCompatImageView.loadImageFromURLWithPlaceholder(url: String, @DrawableRes placeholder: Int) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}
