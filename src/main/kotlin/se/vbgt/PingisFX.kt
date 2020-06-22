package se.vbgt

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
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

        val background1 = Background(
            BackgroundFill(
                Color(0.9, 0.8, 0.8, 1.0),
                CornerRadii.EMPTY,
                Insets.EMPTY
            )
        )
        val background2 = Background(
            BackgroundFill(
                Color(0.8, 0.8, 0.9, 1.0),
                CornerRadii.EMPTY,
                Insets.EMPTY
            )
        )


        val scoreLabel1 = Label(game.getScore(PLAYER_1).toString())
        scoreLabel1.background = background1

        val scoreLabel2 = Label(game.getScore(PLAYER_2).toString())
        scoreLabel2.background = background2

        val nameLabel1 = Label(game.player1.name)
        val nameLabel2 = Label(game.player2.name)

        val setsLabel1 = Label(game.getSetScore(PLAYER_1).toString())
        setsLabel1.background = background1
        val setsLabel2 = Label(game.getSetScore(PLAYER_2).toString())
        setsLabel2.background = background2

        scoreLabel1.underlineProperty().set(game.activePlayer == PLAYER_1)
        scoreLabel2.underlineProperty().set(game.activePlayer == PLAYER_2)

        listOf(
            nameLabel1,
            nameLabel2,
            scoreLabel1,
            scoreLabel2,
            setsLabel1,
            setsLabel2
        ).forEach {
            it.alignment = Pos.BASELINE_CENTER
        }

        game.onGameChange = { changedGame: Game ->
            Platform.runLater {
                scoreLabel1.text = changedGame.getScore(PLAYER_1).toString()
                scoreLabel2.text = changedGame.getScore(PLAYER_2).toString()
                setsLabel1.text = changedGame.getSetScore(PLAYER_1).toString()
                setsLabel2.text = changedGame.getSetScore(PLAYER_2).toString()

                scoreLabel1.underlineProperty().set(changedGame.activePlayer == PLAYER_1)
                scoreLabel2.underlineProperty().set(changedGame.activePlayer == PLAYER_2)

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
                listOf(
                    nameLabel1,
                    nameLabel2,
                    setsLabel1,
                    setsLabel2
                ).forEach {
                    it.font = Font.font("Arial", newValue.toDouble() / 12.0)
                    it.prefHeight = newValue.toDouble() / 8.0
                }

                listOf(scoreLabel1, scoreLabel2).forEach {
                    it.font = Font.font("Arial", newValue.toDouble() / 3.0)
                    it.prefHeight = newValue.toDouble() * 6.0 / 8.0
                }
            }
        }
        main.widthProperty().addListener { _, oldValue, newValue ->
            if (oldValue != newValue) {
                listOf(
                    nameLabel1,
                    nameLabel2,
                    scoreLabel1,
                    scoreLabel2,
                    setsLabel1,
                    setsLabel2
                ).forEach {
                    it.prefWidth = newValue.toDouble() / 2.0
                }
            }
        }

        val myScene = Scene(
            main,
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
            for (i in 1..20) {
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