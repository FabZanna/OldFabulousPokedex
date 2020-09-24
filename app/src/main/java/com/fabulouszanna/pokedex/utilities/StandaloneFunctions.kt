package com.fabulouszanna.pokedex.utilities

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fabulouszanna.pokedex.R
import java.util.*

fun setPokemonSprite(context: Context, url: String, imageView: ImageView) {
    Glide
        .with(context)
        .load(url)
        .error(R.drawable.pokeball)
        .into(imageView)
}

fun extractColorResourceFromType(context: Context, type: String): Int =
    context.resources.getColor(
        context.resources.getIdentifier(
            type.toLowerCase(Locale.ROOT),
            "color",
            context.packageName
        )
    )

fun hideKeyboard(context: Context, view: View) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}