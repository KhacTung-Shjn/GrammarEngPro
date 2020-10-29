package com.example.grammarengpro.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.grammarengpro.R

class CommonUtils {
    companion object {
        fun loadImage(context: Context?, url: String?, imageView: ImageView?) {
            imageView?.let {
                Glide.with(context!!)
                    .load(url)
                    .error(R.drawable.ic_default)
                    .into(it)
            }
        }
    }
}

