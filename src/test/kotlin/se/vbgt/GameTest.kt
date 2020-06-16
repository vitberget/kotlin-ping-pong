package se.vbgt

import junit.framework.TestCase
import se.vbgt.PlayerChoice.PLAYER_1

class GameTest : TestCase() {

    fun testGetWinner() {
        val game = Game(
            player1 = Player("one"),
            player2 = Player("two"),
            numberOfSets = 1,
            setThresholdWin = 3
        )

        game.score(PLAYER_1)
        game.score(PLAYER_1)
        assertFalse(game.isOver())

        game.score(PLAYER_1)
        assertTrue(game.isOver())
        assertEquals(PLAYER_1, game.getWinner())
    }
}