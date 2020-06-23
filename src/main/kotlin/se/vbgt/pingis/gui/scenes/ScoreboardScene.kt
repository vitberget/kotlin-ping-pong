package se.vbgt.pingis.gui.scenes

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import se.vbgt.pingis.Game
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2
import se.vbgt.pingis.gui.PingisFX
import se.vbgt.pingis.gui.backgroundWithColor

fun scoreBoardScene(pingisFX: PingisFX, game: Game): Scene {
    val fontFamily = "Arial"

    val background1 = backgroundWithColor(Color(0.9, 0.8, 0.8, 1.0))
    val background2 = backgroundWithColor(Color(0.8, 0.8, 0.9, 1.0))

    val nameLabel1 = Label(game.player1.name)
    val nameLabel2 = Label(game.player2.name)

    val scoreLabel1 = Label("${game.getScore(PLAYER_1)}")
    val scoreLabel2 = Label("${game.getScore(PLAYER_2)}")

    val setsLabel1 = Label("${game.getSetScore(PLAYER_1)}")
    val setsLabel2 = Label("${game.getSetScore(PLAYER_2)}")

    listOf(scoreLabel1, setsLabel1, nameLabel1).forEach { it.background = background1 }
    listOf(scoreLabel2, setsLabel2, nameLabel2).forEach { it.background = background2 }

    scoreLabel1.underlineProperty().set(game.activePlayer == PLAYER_1)
    scoreLabel2.underlineProperty().set(game.activePlayer == PLAYER_2)

    val smallLabels = listOf(nameLabel1, nameLabel2, setsLabel1, setsLabel2)
    val bigLabels = listOf(scoreLabel1, scoreLabel2)
    val allLabels = smallLabels + bigLabels

    allLabels.forEach {
        it.alignment = Pos.BASELINE_CENTER
    }

    game.onGameChange = { gameState: Game ->
        if (gameState.isOver())
            pingisFX.gameOver(gameState)
        else
            Platform.runLater {
                scoreLabel1.text = "${gameState.getScore(PLAYER_1)}"
                scoreLabel2.text = "${gameState.getScore(PLAYER_2)}"
                setsLabel1.text = "${gameState.getSetScore(PLAYER_1)}"
                setsLabel2.text = "${gameState.getSetScore(PLAYER_2)}"

                scoreLabel1.underlineProperty().set(gameState.activePlayer == PLAYER_1)
                scoreLabel2.underlineProperty().set(gameState.activePlayer == PLAYER_2)
            }
    }

    val main = GridPane()
    main.add(nameLabel1, 0, 0)
    main.add(nameLabel2, 1, 0)
    main.add(setsLabel1, 0, 1)
    main.add(setsLabel2, 1, 1)
    main.add(scoreLabel1, 0, 2)
    main.add(scoreLabel2, 1, 2)

    main.heightProperty().addListener { _, oldValue, newValue ->
        if (oldValue != newValue) {
            val oneEight = newValue.toDouble() / 8.0

            smallLabels.forEach {
                it.font = Font.font(fontFamily, newValue.toDouble() / 12.0)
                it.prefHeight = oneEight
            }

            bigLabels.forEach {
                it.font = Font.font(fontFamily, newValue.toDouble() / 3.0)
                it.prefHeight = oneEight * 6.0
            }
        }
    }
    main.widthProperty().addListener { _, oldValue, newValue ->
        if (oldValue != newValue) {
            allLabels.forEach {
                it.prefWidth = newValue.toDouble() / 2.0
            }
        }
    }

    val myScene = Scene(main)

    myScene.onKeyTyped = EventHandler { event ->
        when (event.character) {
            "z" -> game.score(PLAYER_1)
            "x" -> game.score(PLAYER_2)
        }
    }

    return myScene
}