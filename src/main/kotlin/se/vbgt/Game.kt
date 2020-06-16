package se.vbgt

import se.vbgt.PlayerChoice.PLAYER_1

class Game(
    val player1: Player,
    val player2: Player,
    val numberOfSets: Int = 3,
    val setThresholdWin: Int = 11,
    val ballsBeforeSwitch: Int = 1
) {
    var activePlayer = PLAYER_1
        private set

    var sets = listOf<Set>()
        private set

    private var ballsPlayed = 0


    fun score(whichPlayer: PlayerChoice) {
        require(!isOver())

        val set = when {
            sets.isEmpty() || sets.last().isOver() -> {
                sets = sets + Set(setThresholdWin)
                sets.last()
            }

            else -> sets.last()
        }

        set.score(whichPlayer)

        if(ballsPlayed.inc() >= ballsBeforeSwitch) {
            ballsPlayed = 0
            activePlayer = activePlayer.nextPlayer()
        }
    }


    fun isOver(): Boolean =
        sets.count() == numberOfSets && sets.last().isOver()
}