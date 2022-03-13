package com.efee.bombsort

import android.os.Handler
import android.os.Looper
import java.util.*

class BombLoopTicker(activity: MainActivity) {
    private var timer = Timer()
    val listPointer = activity.bombList
    private val refreshPeriod = 50L
    private val handler =  Handler(Looper.getMainLooper())

    init {
        timer.schedule(BombLoop(), 0,  refreshPeriod)
    }

    inner class BombLoop : TimerTask() {
        override fun run() {
            handler.post {
                for ( bomb in listPointer ) {
                    if (!bomb.isGrabbed && bomb.movementPaused) {
                        bomb.movementPaused = false
                    }
                    if (bomb.isGrabbed) {
                        bomb.animateMe = false
                        bomb.movementPaused = true
                    }
                }
            }
        }
    }

    fun stop(){
        timer.cancel()
    }

    fun resume(){
        timer = Timer()
        timer.schedule(BombLoop(), 0,  refreshPeriod)
    }
}