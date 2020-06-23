package se.vbgt.pingis.gui

import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color

fun backgroundWithColor(color: Color): Background =
    Background(
        BackgroundFill(
            color,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )
    )