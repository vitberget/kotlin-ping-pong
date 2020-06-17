package se.vbgt.pingis

import junit.framework.TestCase
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2

class GameTest : TestCase() {

    fun testGetWinner() {
        val game = Game(
            player1 = Player("one"),
            player2 = Player("two"),
            numberOfSets = 1,
            setThresholdWin = 3
        )

        for (i in 1..2)
            game.score(PLAYER_1)

        assertFalse(game.isOver())

        game.score(PLAYER_1)
        assertTrue(game.isOver())
        assertEquals(PLAYER_1, game.getWinner())
    }

    fun testActivePlayer1() {
        val game = Game(
            player1 = Player("one"),
            player2 = Player("two"),
            ballsBeforeSwitch = 1
        )

        for (i in 1..10) {
            assertEquals(PLAYER_1, game.activePlayer)
            game.score(PLAYER_1)
            assertEquals(PLAYER_2, game.activePlayer)
            game.score(PLAYER_1)
        }
    }

    fun testActivePlayer2() {
        val game = Game(
            player1 = Player("one"),
            player2 = Player("two"),
            ballsBeforeSwitch = 2
        )

        for (i in 1..10) {
            assertEquals(PLAYER_1, game.activePlayer)
            game.score(PLAYER_1)
            assertEquals(PLAYER_1, game.activePlayer)
            game.score(PLAYER_1)

            assertEquals(PLAYER_2, game.activePlayer)
            game.score(PLAYER_2)
            assertEquals(PLAYER_2, game.activePlayer)
            game.score(PLAYER_2)
        }

    }
}