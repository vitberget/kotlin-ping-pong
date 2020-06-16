package se.vbgt

import junit.framework.TestCase
import se.vbgt.PlayerChoice.PLAYER_1
import se.vbgt.PlayerChoice.PLAYER_2
import kotlin.test.assertFails

class SetTest : TestCase() {

    fun testSimpleScore() {
        val set = Set(11)

        set.score(PLAYER_1)

        assertEquals(1, set.scorePlayer1)
        assertEquals(0, set.scorePlayer2)
        assertFalse(set.isOver())
    }

    fun testSimpleWin() {
        val set = Set(11)

        for (i in 1..11)
            set.score(PLAYER_1)

        assertEquals(11, set.scorePlayer1)
        assertEquals(0, set.scorePlayer2)
        assertTrue(set.isOver())
        assertEquals(PLAYER_1, set.getWinner())

        assertFails {
            set.score(PLAYER_1)
        }
        assertFails {
            set.score(PLAYER_2)
        }
    }

    fun testWinWithTwo() {
        val set = Set(11)

        for (i in 1..10) {
            set.score(PLAYER_1)
            set.score(PLAYER_2)
        }

        set.score(PLAYER_1)

        assertEquals(11, set.scorePlayer1)
        assertEquals(10, set.scorePlayer2)
        assertFalse(set.isOver())

        assertFails {
            set.getWinner()
        }

        set.score(PLAYER_1)

        assertEquals(PLAYER_1, set.getWinner())
        assertEquals(12, set.scorePlayer1)
        assertEquals(10, set.scorePlayer2)
        assertTrue(set.isOver())

        assertFails {
            set.score(PLAYER_1)
        }
        assertFails {
            set.score(PLAYER_2)
        }
    }
}