package com.fabulouszanna.pokedex.utilities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fabulouszanna.pokedex.R

fun setPokemonSprite(context: Context, url: String, imageView: ImageView) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.pokeball)
        .into(imageView)
}

fun extractColorResourceFromType(context: Context, type: String): Int =
    context.resources.getColor(context.resources.getIdentifier(type, "color", context.packageName))