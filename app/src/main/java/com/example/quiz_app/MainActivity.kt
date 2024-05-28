package com.example.quiz_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import android.content.Intent
import android.content.SharedPreferences
import com.example.quiz_app.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject


class MainActivity() : AppCompatActivity(), Parcelable {

    private lateinit var binding:ActivityMainBinding
    private val questions = arrayOf("a", "b", "c", "d", "e")
    private val options = arrayOf(arrayOf("Rumunsko", "Čad", "Andorra"),
        arrayOf("Srbsko", "Slovinsko", "Lichtenštejnsko"),
        arrayOf("Bolivie", "Mozambik", "Ghana"),
        arrayOf("Seychely", "Svatá Lucie", "Mauricius"),
        arrayOf("Bhútán", "Barma", "Bangladéš"),
    )

    private val answers = arrayOf(1,2,1,1,0)
    private var currentQuestionIndex = 0
    private var score = 0
    private var playerName: String? = null
    private var playerScores = arrayListOf<PlayerScore>()

    constructor(parcel: Parcel) : this() {
        currentQuestionIndex = parcel.readInt()
        score = parcel.readInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerName = intent.getStringExtra("playerName")
        playerScores = intent.getParcelableArrayListExtra("playerScores") ?: arrayListOf()

        if (playerName.isNullOrEmpty()) {
            Toast.makeText(this, "Player name not found", Toast.LENGTH_SHORT).show()
            finish() // Finish activity if playerName is not provided
            return
        }

        binding.playerNameTextView.text = "Player: $playerName"

        displayQuestion()

        binding.option1Button.setOnClickListener{
            checkAnswer(0)
        }
        binding.option2Button.setOnClickListener{
            checkAnswer(1)
        }
        binding.option3Button.setOnClickListener{
            checkAnswer(2)
        }
    }

    private fun correctAnswerColor(buttonIndex: Int){
        when(buttonIndex){
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongAnswerColor(buttonIndex: Int){
        when(buttonIndex){
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColor(){
        binding.option1Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option2Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option3Button.setBackgroundColor(Color.rgb(50, 59, 96))
    }

    private fun showResult() {
        savePlayerScore(playerName ?: "Unknown", score)
        val intent = Intent(this, PlayerListActivity::class.java)
        startActivity(intent)
    }

    private fun displayQuestion(){
        val currentQuestion = questions[currentQuestionIndex]
        val flagResourceId = when (currentQuestion) {
            "a" -> R.drawable.chad_flag
            "b" -> R.drawable.liechtenstein_flag
            "c" -> R.drawable.mozambique_flag
            "d" -> R.drawable.saint_lucia_flag
            "e" -> R.drawable.bhutan_flag
            else -> R.drawable.chad_flag
        }
        binding.flagImageView.setImageResource(flagResourceId)
        binding.questionText.text = "Vyberte název státu:"

        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]

        resetButtonColor()
    }

    private fun checkAnswer(selectedAnswerIndex: Int){
        val correctAnswerIndex = answers[currentQuestionIndex]

        if(selectedAnswerIndex == correctAnswerIndex){
            score++
            correctAnswerColor(selectedAnswerIndex)
        }else {
            wrongAnswerColor(selectedAnswerIndex)
            correctAnswerColor(correctAnswerIndex)
        }
        if(currentQuestionIndex < questions.size -1){
            currentQuestionIndex++
            binding.questionText.postDelayed({displayQuestion()}, 1000)
        }
        else{
            showResult()
        }
    }

    private fun savePlayerScore(playerName: String, score: Int) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("PlayerScores", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val existingScores = sharedPreferences.getString("scores", "[]")
        val jsonArray = JSONArray(existingScores)

        val playerScoreJson = JSONObject()
        playerScoreJson.put("playerName", playerName)
        playerScoreJson.put("score", score)

        jsonArray.put(playerScoreJson)
        editor.putString("scores", jsonArray.toString())
        editor.apply()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currentQuestionIndex)
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}