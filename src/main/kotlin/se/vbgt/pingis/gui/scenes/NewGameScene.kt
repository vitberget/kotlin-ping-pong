package se.vbgt.pingis.gui.scenes

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Pos.BASELINE_CENTER
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import se.vbgt.pingis.Game
import se.vbgt.pingis.Player
import se.vbgt.pingis.gui.PingisFX
import se.vbgt.pingis.gui.scenes.components.NumberTextField

fun newGameTheme(pingisFX: PingisFX): Scene {

    val title = Label("New game!")
    title.alignment = BASELINE_CENTER
    title.padding = Insets(0.0, 0.0, 100.0, 0.0)

    val main = GridPane()
    main.padding = Insets(20.0)
    main.add(title, 0, 0, 2, 1)

    val labelName1 = Label("Name player 1")
    val labelName2 = Label("Name player 1")
    val labelSets = Label("Number of sets")
    val labelBalls = Label("Balls per set")
    val labelServesPerPlayer = Label("Serves per player")
    main.add(labelName1, 0, 1)
    main.add(labelName2, 0, 2)
    main.add(labelSets, 0, 3)
    main.add(labelBalls, 0, 4)
    main.add(labelServesPerPlayer, 0, 5)

    val labels = listOf(
        labelName1,
        labelName2,
        labelServesPerPlayer,
        labelBalls,
        labelSets
    )

    val name1 = TextField("one")
    val name2 = TextField("two")
    val sets = NumberTextField("5")
    val balls = NumberTextField("11")
    val serves = NumberTextField("1")
    main.add(name1, 1, 1)
    main.add(name2, 1, 2)
    main.add(sets, 1, 3)
    main.add(balls, 1, 4)
    main.add(serves, 1, 5)

    val inputs = listOf(
        name1,
        name2,
        sets,
        balls,
        serves
    )

    val playButton = Button("Play ping-pong!")
    playButton.onAction = EventHandler { event ->
        pingisFX.startGame(
            Game(
                player1 = Player(name1.text.trim()),
                player2 = Player(name2.text.trim()),
                numberOfSets = sets.text.toInt(),
                setThresholdWin = balls.text.toInt(),
                ballsBeforeSwitch = serves.text.toInt()
            )
        )
    }

    main.add(playButton, 1, 6)

    main.heightProperty().addListener { _, oldVal, newVal ->
        if (oldVal != newVal) {
            val f1 = Font(fontFamily, newVal.toDouble() / 20.0)
            val f2 = Font(fontFamily, newVal.toDouble() / 30.0)

            (labels + title).forEach {
                it.font = f1
            }

            inputs.forEach { it.font = f2 }
            playButton.font = f2
        }
    }

    main.widthProperty().addListener { _, oldVal, newVal ->
        if (oldVal != newVal) {
            title.prefWidth = newVal.toDouble()
            (labels + inputs + playButton).forEach { it.prefWidth = newVal.toDouble() / 2.0}
        }
    }

    val scene = Scene(main)

    return scene
}