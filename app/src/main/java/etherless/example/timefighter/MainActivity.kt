package etherless.example.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score: Int = 0
    internal var gameStarted: Boolean = false
    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 60000
    internal var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById<Button>(R.id.tapMeButton)
        gameScoreTextView = findViewById<TextView>(R.id.gameScoreTextView)
        timeLeftTextView = findViewById<TextView>(R.id.timeLeftTextView)

        this.resetGame()

        tapMeButton.setOnClickListener { view ->
            incrementScore()
        }
    }

    private fun setScore() {
        this.gameScoreTextView.text = getString(R.string.yourScore, score.toString())
    }

    private fun incrementScore() {
        this.score += 1;
        this.setScore()
    }

    private fun resetGame() {
        score = 0
        this.setScore()

        val initialTimeLeft = this.initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.timeLeft, initialTimeLeft.toString())

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft.toString())
            }

            override fun onFinish() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        gameStarted = false
    }
}
