package se.vbgt.pingis.gui.scenes.components

import javafx.scene.control.TextField

class NumberTextField(text: String?) : TextField(text) {
    override fun replaceText(start: Int, end: Int, text: String) {
        if (validateNumeric(text)) {
            super.replaceText(start, end, text)
        }
    }

    override fun replaceSelection(text: String) {
        if (validateNumeric(text)) {
            super.replaceSelection(text)
        }
    }

    private fun validateNumeric(text: String): Boolean = text.matches("[0-9]*".toRegex())
}