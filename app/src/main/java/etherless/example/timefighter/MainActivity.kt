package etherless.example.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score: Int = 0
    internal var gameStarted: Boolean = false
    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 5000
    internal var countDownInterval: Long = 1000
    internal val TAG = MainActivity::class.java.simpleName
    internal var timeLeftOnTimer: Long = 60000

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById<Button>(R.id.tapMeButton)
        gameScoreTextView = findViewById<TextView>(R.id.gameScoreTextView)
        timeLeftTextView = findViewById<TextView>(R.id.timeLeftTextView)

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            this.resetGame()
        }

        tapMeButton.setOnClickListener {
            incrementScore()
        }
    }

    private fun setScore() {
        this.gameScoreTextView.text = getString(R.string.yourScore, score.toString())
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        this.score += 1;
        this.setScore()
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun restoreGame() {
        gameScoreTextView.text = getString(R.string.yourScore, score.toString())
        val restoredTime = timeLeftOnTimer / 1000
        timeLeftTextView.text = getString(R.string.timeLeft, restoredTime.toString())

        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }

        countDownTimer.start()
        gameStarted = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving score: $score & Time left $timeLeftOnTimer")

    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy called.")
    }

    private fun resetGame() {
        score = 0
        this.setScore()

        val initialTimeLeft = this.initialCountDown / countDownInterval
        timeLeftTextView.text = getString(R.string.timeLeft, initialTimeLeft.toString())

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / countDownInterval
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft.toString())
            }

            override fun onFinish() {
               endGame()
            }
        }

        gameStarted = false
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.gameOverMessage, score.toString()), Toast.LENGTH_LONG).show()
        resetGame()
    }


}
