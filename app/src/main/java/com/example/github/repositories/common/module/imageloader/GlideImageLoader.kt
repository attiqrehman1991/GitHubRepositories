package com.example.mercariandroidtemplate.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(private val context: Context) : ImageLoader {
    override fun displayImage(url: String?, imageView: ImageView) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
                .into(imageView)
        }
    }
}