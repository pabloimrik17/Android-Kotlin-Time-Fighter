package etherless.example.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById<Button>(R.id.tapMeButton)
        gameScoreTextView = findViewById<TextView>(R.id.gameScoreTextView)
        timeLeftTextView = findViewById<TextView>(R.id.timeLeftTextView)
        this.setScore()

        tapMeButton.setOnClickListener { view ->
            incrementScore()
        }
    }

    private fun setScore() {
        val newScore: String = getString(R.string.yourScore, score.toString())

        this.gameScoreTextView.text = newScore
    }

    private fun incrementScore() {
        this.score += 1;
        this.setScore()
    }
}
