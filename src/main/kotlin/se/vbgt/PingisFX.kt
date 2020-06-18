package se.vbgt

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import se.vbgt.pingis.Game
import se.vbgt.pingis.Player
import se.vbgt.pingis.PlayerChoice
import se.vbgt.pingis.PlayerChoice.PLAYER_1
import se.vbgt.pingis.PlayerChoice.PLAYER_2


class PingisFX : Application() {

    val game = Game(
        player1 = Player("one"),
        player2 = Player("two")
    )

    override fun start(stage: Stage?) {
        requireNotNull(stage)
        println("start stage")

        val label = Label(getPlayerName(game.activePlayer))

        game.onActivePlayerChange = { oldVal: PlayerChoice, newVal: PlayerChoice ->
            Platform.runLater {
                label.text = getPlayerName(newVal)
            }
        }

        val myScene = Scene(
            StackPane(label),
            640.toDouble(),
            480.toDouble()
        )

        stage.apply {
            scene = myScene
            show()
        }

        startThread()
    }

    private fun getPlayerName(newVal: PlayerChoice): String =
        when (newVal) {
            PLAYER_1 -> game.player1.name
            PLAYER_2 -> game.player2.name
        }

    private fun startThread() {
        Thread {
            for (i in 1..10) {
                Thread.sleep(1000)
                println("woho")

                game.score(PLAYER_1)
            }
        }.start()
    }
}

fun main() {
    Application.launch(PingisFX::class.java)
}