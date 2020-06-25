package se.vbgt.pingis.gui.scenes

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.geometry.Pos.BASELINE_CENTER
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import se.vbgt.pingis.Game
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2
import se.vbgt.pingis.gui.PingisFX
import kotlin.math.max

fun gameOverScene(pingisFX: PingisFX, game: Game, gameScene: Scene): Scene {
    val main = GridPane()

    val winner = Label("Winner is ${if (game.getWinner() == PLAYER_1) game.player1.name else game.player2.name}")
    main.add(winner, 0, 0, 2, 1)

    val player1 = Label(game.player1.name)
    val player2 = Label(game.player2.name)
    player1.underlineProperty().value = game.getWinner() == PLAYER_1
    player2.underlineProperty().value = game.getWinner() == PLAYER_2
    player1.background = background1
    player2.background = background2
    main.add(player1, 0, 1)
    main.add(player2, 1, 1)

    val labels = game.sets.mapIndexed { i, s ->
        val p1 = Label(s.scorePlayer1.toString())
        val p2 = Label(s.scorePlayer2.toString())
        p1.underlineProperty().value = s.getWinner() == PLAYER_1
        p2.underlineProperty().value = s.getWinner() == PLAYER_2
        p1.background = background1
        p2.background = background2
        Pair(p1, p2)
    }

    labels.forEachIndexed { i, lbls ->
        main.add(lbls.first, 0, i + 2)
        main.add(lbls.second, 1, i + 2)
    }

    val filler1 = Label()
    val filler2 = Label()
    filler1.background = background1
    filler2.background = background2
    main.add(filler1, 0, 2 + labels.size + 1)
    main.add(filler2, 1, 2 + labels.size + 1)

    val halfLabels = labels.flatMap { listOf(it.first, it.second) } + player1 + player2
    val allLabels = halfLabels + winner

    allLabels.forEach { it.alignment = BASELINE_CENTER }

    main.heightProperty().addListener { _, oldVal, newVal ->
        if (oldVal != newVal) {
            val newDouble = newVal.toDouble()
            val font = Font(fontFamily, newDouble / max(12, 2 + labels.size).toDouble())

            allLabels.forEach { it.font = font }
            listOf(filler1, filler2).forEach { it.prefHeight = newDouble }
        }
    }

    main.widthProperty().addListener { _, oldVal, newVal ->
        if (oldVal != newVal) {
            val newDouble = newVal.toDouble()
            val half = newDouble / 2.0

            winner.prefWidth = newDouble
            (halfLabels + filler1 + filler2).forEach { it.prefWidth = half }
        }
    }

    val scene = Scene(main)
    scene.cursor = Cursor.NONE

    scene.onKeyTyped = EventHandler { event ->
        when (event.character) {
            "u" -> {
                game.undoScore()
                Platform.runLater {
                    pingisFX.stage.scene = gameScene
                    pingisFX.stage.isFullScreen = true
                }
            }
            "n" -> pingisFX.createNewGame()
        }
    }

    return scene
}