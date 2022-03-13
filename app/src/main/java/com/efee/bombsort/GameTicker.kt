package com.efee.bombsort

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.core.content.ContextCompat.startActivity
import java.util.*

class GameTicker(activity: MainActivity) {
    private val activityPointer = activity
    private val redZone = activity.redZone
    private val blueZone = activity.blueZone
    var time = 0
    var secondsGreaterThanTen = 0
    private var timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    inner class GameTickLoop : TimerTask() {
        @SuppressLint("SetTextI18n")
        override fun run() {
            handler.post {
                time++
                if ( secondsGreaterThanTen == 10 ){
                    activityPointer.clock.stop()
                    activityPointer.spawner.stop()
                    activityPointer.animator.stop()
                    activityPointer.bombLoop.stop()
                    activityPointer.movement.stop()

                    while ( activityPointer.bombList.size > 0 )
                        activityPointer.bombList[0].destroy()

                    activityPointer.switchActivityGameOver()
                }
                if ( activityPointer.bombList.size > 10 )
                    secondsGreaterThanTen++
                if ( time % 5 == 0 ){
                    val oldDelay = activityPointer.spawner.refreshPeriod
                    activityPointer.spawner.stop()
                    activityPointer.spawner = SpawnTicker(activityPointer, (oldDelay/1.25).toLong())
                }
                activityPointer.runOnUiThread {
                    activityPointer.timerTextView.text = "Time: $time" }
            }
        }
    }

    init {
        timer.schedule(GameTickLoop(), 0, 1000)
    }

    fun resume(){
        timer = Timer()
        timer.schedule(GameTickLoop(), 0,  1000)
    }

    fun stop(){
        time = 0
        timer.cancel()
    }
}