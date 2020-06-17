package se.vbgt

import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import se.vbgt.pingis.Game
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.Player



fun main() {
    val game = Game(
        Player("Player one"),
        Player("Player two")
    )

    game.score(PLAYER_1)

    if (game.isOver()) {
        println("Game over")
    }




}