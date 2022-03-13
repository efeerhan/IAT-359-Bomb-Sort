package com.efee.bombsort

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*

class BombTouchListener(b : Bomb) : View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    val bombPointer = b
    private val activityPointer = b.activityPointer
    val blueZone = activityPointer.blueZone
    val redZone = activityPointer.redZone
    var originX = 0F
    var originY = 0F

    private fun checkCollision(bomb: Bomb, zone: Rect): Boolean {
        Log.i("Collision", "Bomb: ${bomb.hitBox.left} ${bomb.hitBox.top} ${bomb.hitBox.right} ${bomb.hitBox.bottom}")
        Log.i("Collision", "Zones: ${zone.left} ${zone.top} ${zone.right} ${zone.bottom}")
        return bomb.hitBox.intersect(zone) || zone.contains(bomb.x.toInt(), bomb.y.toInt())
    }

    @SuppressLint("SetTextI18n")
    override fun onTouch(v: View, e: MotionEvent): Boolean {
        if ( e.action == MotionEvent.ACTION_DOWN ) {
            originX = e.rawX
            originY = e.rawY
            v.y = e.rawY - v.height
            v.x = e.rawX - v.width/2
            bombPointer.isGrabbed = true
            bombPointer.movementPaused = true
        }
        if (e.action == MotionEvent.ACTION_MOVE) {
            v.y = e.rawY - v.height
            v.x = e.rawX - v.width/2
            bombPointer.refreshRect()
        }
        if (e.action == MotionEvent.ACTION_UP) {
            val redCollision = checkCollision(bombPointer, redZone)
            val blueCollision = checkCollision(bombPointer, blueZone)

            if ( !redCollision && !blueCollision ) {
                bombPointer.movementPaused = false
                bombPointer.animateMe = true
                bombPointer.xDir = ( ( Random().nextFloat() * 2 ) - 1 ) * 5
                bombPointer.refreshRect()
            }

            if ( redCollision && bombPointer.color != 1 ||
                blueCollision && bombPointer.color != 0 ||
                blueCollision && redCollision ) {

                Log.i("Collision", "Wrong collision!")
                bombPointer.movementPaused = false
                bombPointer.animateMe = true
                bombPointer.xDir = ( ( Random().nextFloat() * 2 ) - 1 ) * 5
                bombPointer.yDir = ( ( Random().nextFloat() * 2 ) - 1 ) * 5
                bombPointer.x = originX
                bombPointer.y = originY
                bombPointer.refreshRect()

            }

            if ( redCollision && bombPointer.color == 1 ||
                blueCollision && bombPointer.color == 0  ) {
                activityPointer.score++
                activityPointer.scoreTextView.text = "Score: ${activityPointer.score}"
                bombPointer.destroy()
            }
            bombPointer.isGrabbed = false
        }
        return true
    }
}