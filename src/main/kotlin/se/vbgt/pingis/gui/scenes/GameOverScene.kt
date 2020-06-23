package se.vbgt.pingis.gui.scenes

import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import se.vbgt.pingis.Game
import se.vbgt.pingis.gui.PingisFX

fun gameOverScene(pingisFX: PingisFX, game: Game): Scene {

    val text = Label("Game over")

    val main = GridPane()
    main.add(text, 0, 0)

    val scene = Scene(main)

    return scene
}