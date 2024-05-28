package com.example.quiz_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayerScoresAdapter(private val playerScores: List<PlayerScore>) :
    RecyclerView.Adapter<PlayerScoresAdapter.PlayerScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player_score, parent, false)
        return PlayerScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlayerScoreViewHolder, position: Int) {
        val playerScore = playerScores[position]
        holder.playerNameTextView.text = playerScore.playerName
        holder.scoreTextView.text = playerScore.score.toString()
    }

    override fun getItemCount(): Int {
        return playerScores.size
    }

    class PlayerScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerNameTextView: TextView = itemView.findViewById(R.id.playerNameTextView)
        val scoreTextView: TextView = itemView.findViewById(R.id.playerScoreTextView)
    }
}