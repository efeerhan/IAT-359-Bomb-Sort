package com.efee.bombsort

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.view.Window
import android.widget.TextView
import android.content.Intent
import androidx.appcompat.widget.AppCompatButton


class GameOverActivity() : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val intentExtras: Bundle? = intent.extras
        val score: Int? = intentExtras?.getInt("score")

        findViewById<TextView>(R.id.gameOverTextView).text = "GAME OVER\nYOUR SCORE: $score"

        findViewById<AppCompatButton>(R.id.returnToStartButton).setOnClickListener(){
            intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}