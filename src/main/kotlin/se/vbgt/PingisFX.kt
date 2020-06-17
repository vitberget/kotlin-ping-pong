package se.vbgt

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class PingisFX : Application() {
    override fun start(stage: Stage?) {
        val label = Label("Hwllo universe")
        val scene = Scene(StackPane(label), 640.0, 480.0)
        stage!!.scene = scene
        stage.show()
    }

    fun main() {
        launch()
    }
}

fun main() {
    PingisFX().main()
}