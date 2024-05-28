package com.example.quiz_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.util.Log
import android.widget.Button
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.ListView
import org.json.JSONArray


class PlayerListActivity : AppCompatActivity() {


    private lateinit var restartButton: Button
    private lateinit var playerListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)

        playerListView = findViewById(R.id.playerListView)
        restartButton = findViewById(R.id.restartButton)

        val sharedPreferences = getSharedPreferences("PlayerScores", Context.MODE_PRIVATE)
        val existingScores = sharedPreferences.getString("scores", "[]")
        val jsonArray = JSONArray(existingScores)

        val playerScoreStrings = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            val playerScoreJson = jsonArray.getJSONObject(i)
            val playerName = playerScoreJson.getString("playerName")
            val score = playerScoreJson.getInt("score")
            playerScoreStrings.add("$playerName: $score")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, playerScoreStrings)
        playerListView.adapter = adapter

        restartButton.setOnClickListener {
            val intent = Intent(this, PlayerNameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}