package se.vbgt

import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import java.util.*


object CollectionsDemo {
    @JvmStatic
    fun main(args: Array<String>) {

        // Use Java Collections to create the List.
        val list: MutableList<String> = ArrayList()

        // Now add observability by wrapping it with ObservableList.
        val observableList = FXCollections.observableList(list)
        observableList.addListener(ListChangeListener<Any?> { println("Detected a change! ") })

        // Changes to the observableList WILL be reported.
        // This line will print out "Detected a change!"

        observableList.add("item one")

        // Changes to the underlying list will NOT be reported
        // Nothing will be printed as a result of the next line.
        list.add("item two")
        println("Size: " + observableList.size)
    }
}