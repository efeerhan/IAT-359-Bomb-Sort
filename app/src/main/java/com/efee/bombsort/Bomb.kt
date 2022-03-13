package com.efee.bombsort

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import java.util.*

@SuppressLint("ViewConstructor")
class Bomb(context: Context, attrs: AttributeSet? = null, var color: Int, activity: MainActivity) : AppCompatImageView(context, attrs) {

    //Color 0 = Blue, 1 = Red
    val hitBox = Rect()
    var xDir : Float = ( ( Random().nextFloat() * 2 ) - 1 ) * 5
    var yDir : Float = 5F
    var isGrabbed = false
    val activityPointer = activity
    var movementPaused = false
    var animateMe = true

    init {
        this.layoutParams = ViewGroup.LayoutParams(150, 150)
        refreshRect()
        setOnTouchListener(BombTouchListener(this))
    }

    fun refreshRect(){
        hitBox.left = (x - 75).toInt()
        hitBox.right = (x + 75).toInt()
        hitBox.top = (y - 75).toInt()
        hitBox.bottom = (y + 75).toInt()
    }

    fun destroy(){
        activityPointer.bombList.remove(this)
        activityPointer.layout.removeView(this)
    }
}