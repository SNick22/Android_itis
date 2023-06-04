package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class PieChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val pieColors = arrayListOf<Int>()
    private val piePercentages = arrayListOf<Int>()

    private val piePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        piePaint.style = Paint.Style.FILL
        textPaint.color = Color.WHITE
        textPaint.textSize = resources.getDimension(R.dimen.text_size)
    }

    fun setData(data: List<Pair<Int, Int>>) {
        pieColors.clear()
        piePercentages.clear()

        for (pair in data) {
            pieColors.add(pair.first)
            piePercentages.add(pair.second)
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        val radius = (width.coerceAtMost(height) / 2).toFloat()
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()

        val rectF = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        var currentAngle = 0f

        for (i in 0 until piePercentages.size) {
            val percentage = piePercentages[i]
            val sweepAngle = 360f * percentage / 100f

            piePaint.color = pieColors[i]

            canvas.drawArc(rectF, currentAngle, sweepAngle, true, piePaint)

            val text = "${(percentage)}%"
            val textX = centerX + radius / 2 * cos(Math.toRadians((currentAngle + sweepAngle / 2).toDouble())).toFloat()
            val textY = centerY + radius / 2 * sin(Math.toRadians((currentAngle + sweepAngle / 2).toDouble())).toFloat()

            canvas.drawText(text, textX, textY, textPaint)

            currentAngle += sweepAngle
        }
    }
}