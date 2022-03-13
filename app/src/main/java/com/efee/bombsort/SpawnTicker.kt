package com.efee.bombsort

import android.os.Handler
import android.os.Looper
import java.util.*

class SpawnTicker(activity: MainActivity, delay: Long) {
    private val activityPointer = activity
    private var timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
    val refreshPeriod = delay

    inner class SpawnTask : TimerTask() {
        override fun run() {
            handler.post {
                activityPointer.runOnUiThread {
                    spawn()
                }
            }
        }
    }

    init {
        timer.schedule(SpawnTask(), 0, refreshPeriod)
    }

    private fun spawn(){
        val tempBomb = Bomb(activityPointer, null, (0..1).random(), activityPointer)
        tempBomb.x = 390F
        tempBomb.y = 76F
        activityPointer.add(tempBomb)
    }

    fun stop(){
        timer.cancel()
    }

    fun resume(){
        timer = Timer()
        timer.schedule(SpawnTask(), 0,  refreshPeriod)
    }
}