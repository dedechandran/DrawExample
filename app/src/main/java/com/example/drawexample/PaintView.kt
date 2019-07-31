package com.example.drawexample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import android.graphics.Paint.DITHER_FLAG


class PaintView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    companion object {
        const val BRUSH_SIZE: Float = 10F
        const val DEFAULT_COLOR = Color.BLACK
        const val TOUCH_TOLERANCE: Float = 4F
    }

    private var currentX: Float = 0F
    private var currentY: Float = 0F
    private lateinit var currentPath: Path
    private val currentPaint: Paint = Paint(DITHER_FLAG)
    private val fingerPaths = mutableListOf<FingerPath>()
    private var currentColor = DEFAULT_COLOR

    init {
        currentPaint.isAntiAlias = true
        currentPaint.isDither = true
        currentPaint.color = currentColor
        currentPaint.style = Paint.Style.STROKE
        currentPaint.strokeJoin = Paint.Join.ROUND
        currentPaint.strokeCap = Paint.Cap.ROUND
        currentPaint.strokeWidth = BRUSH_SIZE
        currentPaint.xfermode = null
        currentPaint.alpha = 0xff
    }

    override fun onDraw(canvas: Canvas?) {
        fingerPaths.forEach {
            currentPaint.color = it.color
            canvas?.drawPath(it.path, currentPaint)
        }
    }

    private fun touchStart(x: Float?, y: Float?) {
        currentPath = Path()
        val fingerPath = FingerPath(currentColor, currentPath)
        fingerPaths.add(fingerPath)
        currentPath.moveTo(x!!, y!!)
        currentX = x
        currentY = y
    }

    private fun touchMove(x: Float?, y: Float?) {
        val dx = abs(x!! - currentX)
        val dy = abs(y!! - currentY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            currentPath.quadTo(currentX, currentY, (x + currentX) / 2, (y + currentY) / 2)
            currentX = x
            currentY = y
        }
    }

    private fun touchUp() {
        currentPath.lineTo(currentX, currentY)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
            }
        }
        invalidate()
        return true
    }

    fun clear() {
        fingerPaths.clear()
        invalidate()
    }

    fun setPaintColor(color: Int) {
        currentColor = color
    }
}