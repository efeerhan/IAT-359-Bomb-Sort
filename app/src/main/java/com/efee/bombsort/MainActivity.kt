package com.efee.bombsort

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    var bombList: MutableList<Bomb> = mutableListOf()
    lateinit var layout: ConstraintLayout
    lateinit var blueZone: Rect
    lateinit var redZone: Rect

    lateinit var timerTextView: TextView
    lateinit var scoreTextView: TextView
    lateinit var clock : GameTicker
    lateinit var spawner : SpawnTicker
    lateinit var animator : BombAnimTicker
    lateinit var bombLoop : BombLoopTicker
    lateinit var movement : BombMovementTicker

    var score = 0

    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.mainLayout)

        val displayHeight = Resources.getSystem().displayMetrics.heightPixels
        val displayWidth = Resources.getSystem().displayMetrics.widthPixels

        blueZone = Rect(0, displayHeight / 2, displayWidth / 2, displayHeight - 100)
        redZone = Rect(blueZone.right, blueZone.top, displayWidth, displayHeight - 100)

        Log.i("Red Bounds", "${redZone.left} ${redZone.top} ${redZone.right} ${redZone.bottom}")
        Log.i("Blue Bounds", "${blueZone.left} ${blueZone.top} ${blueZone.right} ${blueZone.bottom}")

        timerTextView = findViewById(R.id.timerTextView)
        scoreTextView = findViewById(R.id.scoreTextView)

        clock = GameTicker(this)
        animator = BombAnimTicker(this)
        bombLoop = BombLoopTicker(this)
        spawner = SpawnTicker(this, 1500)
        val bomb = Bomb(this, null, (0..1).random(), this).apply {
            x = 390F
            y = 76F }
        add(bomb)
        movement = BombMovementTicker(bombList)

        findViewById<ResetButton>(R.id.resetButton).initialize(this)
    }

    fun switchActivityGameOver(){
        intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
    }

    fun add(bomb: Bomb){
        bombList.add(bomb)
        layout.addView(bomb)
    }
}