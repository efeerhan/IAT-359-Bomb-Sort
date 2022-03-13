package com.efee.bombsort

import android.os.Handler
import android.os.Looper
import java.util.*

class BombAnimTicker(activity: MainActivity) {
    private val refreshPeriod = 300L
    var frame = 0
    val listPointer = activity.bombList
    private var timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    inner class AnimTickLoop : TimerTask() {
        override fun run() {
            handler.post {
                if (frame == 1) {
                    frame = 0
                    for (bomb in listPointer) {
                        if ( bomb.animateMe ) {
                            if (bomb.color == 0)
                                bomb.setImageResource(R.drawable.blue1)
                            else
                                bomb.setImageResource(R.drawable.red1)
                        }
                    }
                } else {
                    frame = 1
                    for (bomb in listPointer) {
                        if ( bomb.animateMe ) {
                            if (bomb.color == 0)
                                bomb.setImageResource(R.drawable.blue2)
                            else
                                bomb.setImageResource(R.drawable.red2)
                        }
                    }
                }
            }
        }
    }

    init {
        timer.schedule(AnimTickLoop(), 0,  refreshPeriod)
    }

    fun stop(){
        timer.cancel()
    }

    fun resume(){
        timer = Timer()
        timer.schedule(AnimTickLoop(), 0,  refreshPeriod)
    }
}