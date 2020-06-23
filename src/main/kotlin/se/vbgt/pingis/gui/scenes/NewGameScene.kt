package se.vbgt.pingis.gui.scenes

import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import se.vbgt.pingis.gui.PingisFX

fun newGameTheme(pingisFX: PingisFX): Scene {

    val text = Label("New game")

    val main = GridPane()
    main.add(text, 0, 0)

    val scene = Scene(main)

    return scene
}