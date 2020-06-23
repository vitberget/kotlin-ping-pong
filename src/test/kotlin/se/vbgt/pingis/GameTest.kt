package se.vbgt.pingis

import junit.framework.TestCase
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2
import kotlin.test.assertFails

class GameTest : TestCase() {


    fun testEvenNumberOfSetsFails() {
        for (sets in 2..100 step 2) {
            assertFails {
                Game(
                    player1 = Player("one"),
                    player2 = Player("two"),
                    numberOfSets = sets
                )
            }
        }
    }

    fun testBadNumberOfSetsFails() {
        for (sets in listOf(0, -1, -2, -3)) {
            assertFails {
                Game(
                    player1 = Player("one"),
                    player2 = Player("two"),
                    numberOfSets = sets
                )
            }
        }
    }

    fun testOddNumberOfSets() {
        for (sets in 1..99 step 2) {
            Game(
                player1 = Player("one"),
                player2 = Player("two"),
                numberOfSets = sets
            )
        }
    }

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

    fun testGetWinner11() {
        val game = Game(
            player1 = Player("one"),
            player2 = Player("two"),
            numberOfSets = 5,
            setThresholdWin = 11
        )

        for (i in 1..32)
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
            assertEquals(PLAYER_1, game.getActivePlayer())
            game.score(PLAYER_1)
            assertEquals(PLAYER_2, game.getActivePlayer())
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
            assertEquals(PLAYER_1, game.getActivePlayer())
            game.score(PLAYER_1)
            assertEquals(PLAYER_1, game.getActivePlayer())
            game.score(PLAYER_1)

            assertEquals(PLAYER_2, game.getActivePlayer())
            game.score(PLAYER_2)
            assertEquals(PLAYER_2, game.getActivePlayer())
            game.score(PLAYER_2)
        }

    }
}