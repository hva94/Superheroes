package com.hvasoft.superheroes.core

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun Fragment.showMessage(msgRes: Int, isError: Boolean = false) {
        val duration = if (isError) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        view?.let { Snackbar.make(it, msgRes, duration).show() }
    }

    fun ImageView.loadImage(url: String?) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(this)
    }

}