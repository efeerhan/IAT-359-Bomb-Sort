package com.efee.bombsort

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import java.lang.Thread.sleep

class ResetButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @SuppressLint("SetTextI18n")
    fun initialize(activity: MainActivity) {
        setOnClickListener {
            activity.clock.stop()
            activity.spawner.stop()
            activity.animator.stop()
            activity.bombLoop.stop()
            activity.movement.stop()

            while ( activity.bombList.size > 0 )
                activity.bombList[0].destroy()

            activity.score = 0
            activity.scoreTextView.text = "Score: ${activity.score}"

            activity.clock.resume()
            activity.spawner.resume()
            activity.animator.resume()
            activity.bombLoop.resume()
            activity.movement.resume()
        }
    }
}