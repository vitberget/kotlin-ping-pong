package se.vbgt

import se.vbgt.PlayerChoice.PLAYER_1

fun main() {
    val game = Game(
        Player("Player one"),
        Player("Player two")
    )

    game.score(PLAYER_1)

    if(game.isOver()) {
        println("Game over")
    }
}