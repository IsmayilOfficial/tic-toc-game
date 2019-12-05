package ut.ee.cs.tictocgame

import android.widget.ImageView

object logic {
    private lateinit var sBlocks: Array<ImageView?>
    var sWinner: String? = null
    var sSet = 0
    const val CIRCLE = 0
    const val CROSS = 1
    private fun areSameInSet(first: Int, second: Int, third: Int, set: Int): Boolean {
        val value =
            sBlocks[first - 1]?.id == sBlocks[second - 1]?.id && sBlocks[second - 1]?.id == sBlocks[third - 1]?.id
        if (value) {
            sWinner = if (sBlocks[first - 1]?.id == CIRCLE) "CIRCLE" else "CROSS"
            sSet = set
        }
        return value
    }

    fun isCompleted(position: Int, blocks: Array<ImageView?>): Boolean {
        sBlocks = blocks
        var isComplete = false
        when (position) {
            1 -> isComplete = areSameInSet(1, 2, 3, 1) ||
                    areSameInSet(1, 4, 7, 4) ||
                    areSameInSet(1, 5, 9, 7)
            2 -> isComplete = areSameInSet(1, 2, 3, 1) ||
                    areSameInSet(2, 5, 8, 5)
            3 -> isComplete = areSameInSet(1, 2, 3, 1) ||
                    areSameInSet(3, 6, 9, 6) ||
                    areSameInSet(3, 5, 7, 8)
            4 -> isComplete = areSameInSet(4, 5, 6, 2) ||
                    areSameInSet(1, 4, 7, 4)
            5 -> isComplete = areSameInSet(4, 5, 6, 2) ||
                    areSameInSet(2, 5, 8, 5) ||
                    areSameInSet(1, 5, 9, 7) ||
                    areSameInSet(3, 5, 7, 8)
            6 -> isComplete = areSameInSet(4, 5, 6, 2) ||
                    areSameInSet(3, 6, 9, 6)
            7 -> isComplete = areSameInSet(7, 8, 9, 3) ||
                    areSameInSet(1, 4, 7, 4) ||
                    areSameInSet(3, 5, 7, 8)
            8 -> isComplete = areSameInSet(7, 8, 9, 3) ||
                    areSameInSet(2, 5, 8, 5)
            9 -> isComplete = areSameInSet(7, 8, 9, 3) ||
                    areSameInSet(3, 6, 9, 6) ||
                    areSameInSet(1, 5, 9, 7)
        }
        return isComplete
    }
}