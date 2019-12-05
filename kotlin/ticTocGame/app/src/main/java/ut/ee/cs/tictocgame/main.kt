package ut.ee.cs.tictocgame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class main : Activity(){
    private val mBlocks =
        arrayOfNulls<ImageView>(9)
    private var mDisplay: TextView? = null

    private enum class TURN {
        CIRCLE, CROSS
    }

    private var mTurn: TURN? = null
    private var mExitCounter = 0
    private var mStatusCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeScreen()
        initialize()
    }

    private fun makeScreen() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions

    }

    private fun initialize() {
        val exit =
            findViewById<View>(R.id.exit) as ImageView
        exit.setOnClickListener {
            if (mExitCounter == 1) {
                finish()
                System.exit(0)
            } else {
                mExitCounter++
                Toast.makeText(
                    applicationContext,
                    "Press again to exit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        mDisplay = findViewById<View>(R.id.display_board) as TextView
        val replay =
            findViewById<View>(R.id.replay) as ImageView
        replay.setOnClickListener {
            val starter = intent
            finish()
            starter.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(starter)
        }
        for (position in 0..8) {
            val resId =
                resources.getIdentifier("block_" + (position + 1), "id", packageName)
            mBlocks[position] =
                findViewById<View>(resId) as ImageView
            mBlocks[position]!!.setOnClickListener { switchTurn(position) }
        }
    }

    private fun switchTurn(position: Int) {
        if (mTurn == TURN.CIRCLE) {
            mBlocks[position]!!.setImageResource(R.drawable.circle)
            mBlocks[position]!!.id = logic.CIRCLE
            mTurn = TURN.CROSS
            mDisplay!!.text = "CROSS's turn"
        } else {
            mBlocks[position]!!.setImageResource(R.drawable.cross)
            mBlocks[position]!!.id = logic.CROSS
            mTurn = TURN.CIRCLE
            mDisplay!!.text = "CIRCLE's turn"
        }
        mBlocks[position]!!.isEnabled = false
        mStatusCounter++
        if (logic.isCompleted(position + 1, mBlocks)) {
            mDisplay!!.setText(logic.sWinner.toString() + " won")
            displayStick(logic.sSet)
            disableAll()
        } else if (mStatusCounter == 9) {
            mDisplay!!.text = "DRAW. Try again"
        }
    }

    private fun displayStick(stick: Int) {
        val view: View = when (stick) {
            1 -> findViewById(R.id.top_horizontal)
            2 -> findViewById(R.id.center_horizontal)
            3 -> findViewById(R.id.bottom_horizontal)
            4 -> findViewById(R.id.left_vertical)
            5 -> findViewById(R.id.center_vertical)
            6 -> findViewById(R.id.right_vertical)
            7 -> findViewById(R.id.left_right_diagonal)
            8 -> findViewById(R.id.right_left_diagonal)
            else -> findViewById(R.id.top_horizontal)
        }
        view.visibility = View.VISIBLE
    }

    private fun disableAll() {
        for (i in 0..8) mBlocks[i]!!.isEnabled = false
    }

    override fun onBackPressed() {}
}