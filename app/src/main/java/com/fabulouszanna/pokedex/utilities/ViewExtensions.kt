package com.fabulouszanna.pokedex.utilities

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()