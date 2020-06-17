package se.vbgt.pingis

import se.vbgt.pingis.PlayerChoice
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2

class Set(val thresholdWin: Int) {
    var scorePlayer1 = 0
        private set
    var scorePlayer2 = 0
        private set

    fun score(whichPlayer: PlayerChoice) {
        require(!isOver())

        when (whichPlayer) {
            PLAYER_1 -> scorePlayer1 = scorePlayer1.inc()
            PLAYER_2 -> scorePlayer2 = scorePlayer2.inc()
        }
    }

    fun getWinner(): PlayerChoice {
        require(isOver())

        return if (scorePlayer1 > scorePlayer2)
            PLAYER_1
        else
            PLAYER_2
    }

    fun isOver(): Boolean =
        when {
            scorePlayer1 >= thresholdWin && scorePlayer1 > scorePlayer2 + 1 -> true
            scorePlayer2 >= thresholdWin && scorePlayer2 > scorePlayer1 + 1 -> true

            else -> false
        }
}