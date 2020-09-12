package com.fabulouszanna.pokedex.ui.customviews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType

class MoveTypeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    private val rectColor = ContextCompat.getColor(context, R.color.darkGray)
    var typePaintColor = extractColorResourceFromType(context, "bug")
    private val typePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = rectColor
        style = Paint.Style.FILL
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        val sizeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics)
        textSize = sizeInPx
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private var textXShift = 0f
    private val textBound = Rect()

    private fun getCoordinates(): List<List<Float>> {
        val height = this.height.toFloat()
        if (textXShift == 0f) {
            textXShift = width * 0.2f
        }

        val topRight = listOf(textXShift, 0f)
        val bottomRight = listOf(width * 0.15f, height)
        val bottomLeft = listOf(0f, height)
        return listOf(topRight, bottomRight, bottomLeft)
    }

    private fun getColoredPath(): Path {
        val path = Path()
        path.apply {
            getCoordinates().forEach {
                val x = it[0]
                val y = it[1]
                lineTo(x, y)
            }
            close()
        }
        return path
    }

    override fun onDraw(canvas: Canvas?) {
        val path = getColoredPath()
//        val textBounds = Rect()
        textPaint.getTextBounds(text.toString(), 0, text.length, textBound)
        typePaint.color = typePaintColor
        canvas?.run {
            drawRect(0f, 0f, width.toFloat(), height.toFloat(), rectPaint)
            drawPath(path, typePaint)
//            invalidate()
//            drawText(text, 0, text.length, textXShift, height.toFloat(), textPaint)
        }
        super.onDraw(canvas)
    }
}