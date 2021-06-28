package ca.nait.dmit.dmit2504.coroutineracinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val MAX_TIME_SECONDS = 30
        const val INCREMENT_TIME_MILLISECONDS = 1000L
    }

    private val timerTextView: TextView by lazy { findViewById(R.id.activity_main_timervalue_textview) }
    private val timerProgressBar: ProgressBar by lazy { findViewById(R.id.activity_main_timer_progressbar) }

    private val computerTextView: TextView by lazy { findViewById(R.id.activity_main_computervalue_textview) }
    private val computerSeekBar: SeekBar by lazy { findViewById(R.id.activity_main_computer_seekbar) }

    private val playerTextView: TextView by lazy { findViewById(R.id.activity_main_playervalue_textview) }
    private val playerSeekBar: SeekBar by lazy { findViewById(R.id.activity_main_player_seekbar) }

    private val startGameButton: Button by lazy { findViewById(R.id.activity_main_startgame_button) }
    private val endGameButton: Button by lazy { findViewById(R.id.activity_main_endgame_button) }
    private val runPlayerButton: Button by lazy { findViewById(R.id.activity_main_run_button) }

    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetGame()
    }

    fun onStartGameClick(view: View) {
        startGameButton.visibility = View.GONE
        endGameButton.visibility = View.VISIBLE
        runPlayerButton.visibility = View.VISIBLE

        gameOver = false

        computerMove()


        var timerValue = 1
        timerProgressBar.isIndeterminate = true
        while (!gameOver) {
            Thread.sleep(INCREMENT_TIME_MILLISECONDS)
            timerTextView.setText("$timerValue")
            timerValue++
        }
        timerProgressBar.isIndeterminate = false
    }

    fun computerMove() {
        // Computer moves every 500 to 1000 milliseconds
        while (!gameOver) {
            val randomProgress = Random.nextLong(100,500)
            Thread.sleep(randomProgress)
            computerSeekBar.incrementProgressBy(1)
            computerTextView.setText("${computerSeekBar.progress}")
            if (computerSeekBar.progress >= MAX_TIME_SECONDS) {
                Toast.makeText(this,"Computer wins", Toast.LENGTH_LONG).show()
                resetGame()
            }
        }
    }

    fun onEndGameClick(view: View) {
        resetGame()
    }

    fun onRunPlayerClick(view: View) {
        playerSeekBar.incrementProgressBy(1)
        playerTextView.setText("${playerSeekBar.progress}")
        if (playerSeekBar.progress >= MAX_TIME_SECONDS) {
            Toast.makeText(this,"Player wins", Toast.LENGTH_LONG).show()
            resetGame()
        }
    }

    fun resetGame() {
        gameOver = true
        startGameButton.visibility = View.VISIBLE
        endGameButton.visibility = View.GONE
        runPlayerButton.visibility = View.GONE

        timerProgressBar.isIndeterminate = false
        timerTextView.setText("Timer Value")
        computerSeekBar.progress = 1
        computerTextView.setText("Computer Value")
        playerSeekBar.progress = 1
        playerTextView.setText("Player Value")
    }
}