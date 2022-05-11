package com.example.mercariandroidtemplate.imageloader

import android.widget.ImageView

interface ImageLoader {
    fun displayImage(url: String?, imageView: ImageView)
//    fun displayBackground(url: String?, defaultImage:Int, imageView: ImageView)
}