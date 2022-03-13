package com.efee.bombsort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val start = findViewById<Button>(R.id.startButton)
        start.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}