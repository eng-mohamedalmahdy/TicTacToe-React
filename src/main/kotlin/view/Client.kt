package view

import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.create

fun main() {
    window.onload = {
        val container = document.getElementById("root") ?: error("Couldn't find root container!")
        render(TicTacToeBoard.create(), container)
    }
}
/*
state <=> view
 */