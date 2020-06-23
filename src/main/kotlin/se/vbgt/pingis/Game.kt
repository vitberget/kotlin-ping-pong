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

    var activePlayer: PlayerChoice = PLAYER_1
        private set


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

    private var ballsPlayed = 0

    fun score(whichPlayer: PlayerChoice) {
        require(!isOver())

        ballsPlayed = ballsPlayed.inc()
        maybeSwitchPlayer()

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

    private fun maybeSwitchPlayer() {
        if (ballsPlayed > 0 && ballsPlayed % ballsBeforeSwitch == 0) {
            activePlayer = activePlayer.nextPlayer()
        }
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
            .sortedBy { it.second }
            .first()
            .first
    }

    private fun foldFun(acc: Map<PlayerChoice, Int>, pc: PlayerChoice): Map<PlayerChoice, Int> =
        acc.plus(pc to acc.getOrDefault(pc, 0).inc())
    
    var onGameChange: ((Game) -> Unit)? = null
}