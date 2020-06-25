package se.vbgt.pingis

import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2

class Game(
    val player1: Player,
    val player2: Player,
    val numberOfSets: Int = 5,
    val setThresholdWin: Int = 11,
    val ballsBeforeSwitch: Int = 1
) {
    init {
        require(numberOfSets >= 1) { "Must have sets" }
        require(numberOfSets % 2 == 1) { "Must have odd number of sets" }
        require(setThresholdWin >= 2) { "Must have at least two balls per set" }
    }

    var sets = listOf<Set>()
        private set

    fun getScore(playerChoice: PlayerChoice): Int =
        if (sets.isEmpty() || sets.last().isOver())
            0
        else
            when (playerChoice) {
                PLAYER_1 -> sets.last().scorePlayer1
                PLAYER_2 -> sets.last().scorePlayer2
            }

    fun getSetScore(playerChoice: PlayerChoice): Int =
        if (sets.isEmpty())
            0
        else
            sets.filter { it.isOver() }
                .map { it.getWinner() }
                .filter { it == playerChoice }
                .count()

    private var ballsWon = listOf<PlayerChoice>()

    fun getActivePlayer(): PlayerChoice {
        val divided = ballsWon.size / ballsBeforeSwitch
        val even = divided % 2 == 0
        return if (even) PLAYER_1 else PLAYER_2
    }

    fun score(whichPlayer: PlayerChoice) {
        require(!isOver())

        ballsWon = ballsWon + whichPlayer
//        maybeSwitchPlayer()

        sets = maybeAddSet()
        sets.last().score(whichPlayer)

        onGameChange?.invoke(this)
    }

    private fun maybeAddSet(): List<Set> {
        return if (sets.isEmpty() || sets.last().isOver())
            sets + Set(setThresholdWin)
        else
            sets
    }
//
//    private fun maybeSwitchPlayer() {
//        val ballsPlayed = ballsWon.size
//        if (ballsPlayed > 0 && ballsPlayed % ballsBeforeSwitch == 0) {
//            activePlayer = activePlayer.nextPlayer()
//        }
//    }

    fun undoScore() {
        val tempBallsWon = ballsWon.dropLast(1)
        sets = listOf()
        ballsWon = listOf()

        tempBallsWon.forEach {
            score(it)
        }

        onGameChange?.invoke(this)
    }

    fun isOver(): Boolean =
        sets.filter { it.isOver() }
            .map { it.getWinner() }
            .fold(mapOf(), ::foldFun)
            .any { it.value > numberOfSets / 2 }

    fun getWinner(): PlayerChoice {
        require(isOver())

        return sets.map { it.getWinner() }
            .fold(mapOf(), ::foldFun)
            .toList()
            .sortedByDescending { it.second }
            .first()
            .first
    }

    private fun foldFun(acc: Map<PlayerChoice, Int>, pc: PlayerChoice): Map<PlayerChoice, Int> =
        acc.plus(pc to acc.getOrDefault(pc, 0).inc())

    var onGameChange: ((Game) -> Unit)? = null
}