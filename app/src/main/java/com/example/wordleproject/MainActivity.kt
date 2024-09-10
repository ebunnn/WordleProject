package com.example.wordleproject

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.wordleproject.FourLetterWordList

class MainActivity : AppCompatActivity() {
    private lateinit var wordToGuess: String
    private var guessCount = 0
    private val maxGuesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etSimple = findViewById<EditText>(R.id.et_simple)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvGuess1 = findViewById<TextView>(R.id.tv_guess_1)
        val tvCheck1 = findViewById<TextView>(R.id.tv_check_1)
        val tvGuess2 = findViewById<TextView>(R.id.tv_guess_2)
        val tvCheck2 = findViewById<TextView>(R.id.tv_check_2)
        val tvGuess3 = findViewById<TextView>(R.id.tv_guess_3)
        val tvCheck3 = findViewById<TextView>(R.id.tv_check_3)

        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        btnSubmit.setOnClickListener {
            val guess = etSimple.text.toString().trim()
            if (guess.length == 4) {
                val result = checkGuess(guess)
                Log.d("MainActivity", "Guess: $guess, Result: $result")

                // Handle displaying the guess and result
                displayGuess(guess, result)

                // Increment guessCount only after handling the guess
                guessCount++

                // Clear the input field
                etSimple.text.clear()

                // Check if the game is over
                if (guessCount >= maxGuesses || result == "OOOO") {
                    findViewById<TextView>(R.id.tv_final_word).text = wordToGuess
                    Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkGuess(guess: String): String {
        val upperGuess = guess.uppercase()
        var result = ""
        if (upperGuess.length != wordToGuess.length) {
            return "Invalid length"
        }
        for (i in upperGuess.indices) {
            result += when {
                upperGuess[i] == wordToGuess[i] -> "O"
                upperGuess[i] in wordToGuess -> "+"
                else -> "X"
            }
        }
        return result
    }

    private fun displayGuess(guess: String, result: String) {
        when (guessCount) {
            0 -> {
                findViewById<TextView>(R.id.tv_guess_1).text = guess
                findViewById<TextView>(R.id.tv_check_1).text = result
            }
            1 -> {
                findViewById<TextView>(R.id.tv_guess_2).text = guess
                findViewById<TextView>(R.id.tv_check_2).text = result
            }
            2 -> {
                findViewById<TextView>(R.id.tv_guess_3).text = guess
                findViewById<TextView>(R.id.tv_check_3).text = result
            }
        }
    }
}
