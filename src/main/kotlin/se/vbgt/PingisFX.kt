package se.vbgt

import javafx.application.Application
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.util.ArrayList
import java.util.concurrent.Callable


class PingisFX : Application() {
    private lateinit var observableList: ObservableList<String>

    override fun start(stage: Stage?) {
        requireNotNull(stage)
        println("start stage")

        val label = Label()
        val list: MutableList<String> = ArrayList()
        list.add("hello")

        observableList = FXCollections.observableList(list)

        label.textProperty().bind(
            Bindings.createStringBinding(
                Callable { observableList.last() },
                observableList
            )
        )


        val scene = Scene(StackPane(label), 640.0, 480.0)
        stage.scene = scene
        stage.show()

        startThread()

    }

    private fun startThread() {
        Thread {
            Thread.sleep(1000)
            println("woho")
            Platform.runLater { observableList.add("woho") }
        }.start()

    }
}

fun main() {
    Application.launch(PingisFX::class.java)
}