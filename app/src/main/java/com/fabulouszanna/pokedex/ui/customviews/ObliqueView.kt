package com.fabulouszanna.pokedex.ui.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.fabulouszanna.pokedex.R

class ObliqueView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var obliqueColor: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ObliqueView, 0, 0)
        typedArray.apply {
            try {
                obliqueColor = getColor(R.styleable.ObliqueView_obliqueColor, 0)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ResourcesCompat.getColor(resources, R.color.darkGray, null)
    }

    private val obliquePaint = Paint(backgroundPaint)

    private fun getObliquePath(): Path = Path().apply {
        when (obliqueColor) {
            Color.WHITE -> {
                lineTo(width.toFloat(), 0f)
                lineTo(0f, height.toFloat())
            }
            else -> {
                lineTo(width * 0.9f, 0f)
                lineTo(width * 0.6f, height.toFloat())
                lineTo(0f, height.toFloat())
            }
        }

    }

    override fun onDraw(canvas: Canvas?) {
        val obliquePath = getObliquePath()
        obliquePaint.color = obliqueColor
        canvas?.run {
            drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
            drawPath(obliquePath, obliquePaint)
        }
        super.onDraw(canvas)
    }
}