package com.example.quiz_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class PlayerNameActivity : AppCompatActivity() {

    private lateinit var playerNameEditText: EditText
    private lateinit var startQuizButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_name)

        playerNameEditText = findViewById(R.id.playerNameEditText)
        startQuizButton = findViewById(R.id.startQuizButton)

        startQuizButton.setOnClickListener {
            val playerName = playerNameEditText.text.toString()
            if (playerName.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("playerName", playerName)
                startActivity(intent)
            }
            else {
                playerNameEditText.error = "Please enter your name"}
        }
    }
}