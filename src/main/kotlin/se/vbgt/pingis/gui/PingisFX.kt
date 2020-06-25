package se.vbgt.pingis.gui

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import se.vbgt.pingis.Game
import se.vbgt.pingis.Player
import se.vbgt.pingis.gui.scenes.gameOverScene
import se.vbgt.pingis.gui.scenes.newGameTheme
import se.vbgt.pingis.gui.scenes.scoreBoardScene


class PingisFX : Application() {

    lateinit var stage: Stage

    val newGameScene = newGameTheme(this)

    override fun start(stage: Stage?) {
        requireNotNull(stage)
        println("start stage")

        this.stage = stage

        stage.apply {
            fullScreenExitHint = ""
            show()
        }

        createNewGame()
    }

    fun createNewGame() {
        Platform.runLater {
            stage.scene = newGameScene
            stage.isFullScreen = true
        }
    }

    fun startGame(game: Game) {
        val scoreBoardScene = scoreBoardScene(this, game)

        Platform.runLater {
            stage.scene = scoreBoardScene
            stage.isFullScreen = true
        }
    }

    fun gameOver(game: Game, scene: Scene) {
        val gameOverScene = gameOverScene(this, game, scene)

        Platform.runLater {
            stage.scene = gameOverScene
            stage.isFullScreen = true
        }
    }
}

fun main() {
    Application.launch(PingisFX::class.java)
}