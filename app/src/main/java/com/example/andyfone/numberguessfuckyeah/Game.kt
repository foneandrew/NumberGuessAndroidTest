package com.example.andyfone.numberguessfuckyeah

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.*
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    private var hiddenNumber: Int by Delegates.notNull<Int>()
    private var maxRange: Int by Delegates.notNull<Int>()
    private var minRange: Int by Delegates.notNull<Int>()
    private var lives: Int by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        maxRange = 100
        minRange = 0
        lives = 6
        setDisplayedRange()
        setRandomNumber()
        TextInfo.text = getText(R.string.welcome_message)
    }

    fun restart(view: View) {
        finish()
        startActivity(getIntent())
    }

    fun guessNumber(view: View) {
        try {
            val guess = Integer.parseInt(numberEntry.text.toString())

            var message: String
            var livesMessage: String

            when {
                guess > hiddenNumber -> {
                    if (guess < maxRange){
                        maxRange = guess
                        setDisplayedRange()
                    }
                    lives--
                    message = getString(R.string.too_high_message)
                }
                guess < hiddenNumber -> {
                    if (guess > minRange){
                        minRange = guess
                        setDisplayedRange()
                    }
                    lives--
                    message = getString(R.string.too_low_message)
                }
                else -> {
                    HiddenNumberRange.text = hiddenNumber.toString()
                    message = getString(R.string.victory_message)
                    button.isEnabled = false
                }
            }

            if (lives < 1) {
                message = "You lose!"
                livesMessage = "You suck"
                button.isEnabled = false
            } else {
                livesMessage = "You have ${lives} lives left"
            }

            TextInfo.text = livesMessage
            TextInstruction.text = message
            numberEntry.text.clear()
        } catch (e: NumberFormatException) {
            TextInstruction.text = "numbers only"
            return
        }
    }

    private fun setDisplayedRange() {
        HiddenNumberRange.text = "${minRange} - ${maxRange}"
    }

    private fun setRandomNumber() {
        var random = Random()
        hiddenNumber = random.nextInt(101)
    }
}
