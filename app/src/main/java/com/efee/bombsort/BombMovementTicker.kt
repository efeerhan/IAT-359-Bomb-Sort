package com.efee.bombsort

import android.util.Log
import java.util.*

class BombMovementTicker(bombList: MutableList<Bomb>) {
    private val refreshPeriod = 50L

    val listPointer = bombList
    private var timer = Timer()

    inner class MovementTickLoop : TimerTask() {
        override fun run() {
            val iterate = listPointer.listIterator()
            try {
                while (iterate.hasNext()) {
                    val bomb = iterate.next()
                    if (!bomb.movementPaused) {
                        checkPosition(bomb)
                        bomb.x += bomb.xDir
                        bomb.y += bomb.yDir
                        bomb.refreshRect()
                    }
                }
            } catch (e: ConcurrentModificationException) { Log.e("Bomb Movement Thread", "CME") }
        }
    }

    init {
        timer.schedule(MovementTickLoop(), 0, refreshPeriod)
    }

    private fun checkPosition(bomb: Bomb){
        if ( bomb.x + bomb.xDir < 0 || bomb.x + bomb.xDir > 950 )
            bomb.xDir *= -1
        if ( bomb.y + bomb.yDir < 75 || bomb.y + bomb.yDir > 820 )
            bomb.yDir *= -1
    }

    fun stop(){
        timer.cancel()
    }

    fun resume(){
        timer = Timer()
        timer.schedule(MovementTickLoop(), 0, refreshPeriod)
    }
}