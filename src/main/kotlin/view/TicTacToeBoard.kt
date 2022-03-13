package view

import csstype.*
import kotlinext.js.getOwnPropertyNames
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tr
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.img
import react.useEffect
import react.useState


val TicTacToeBoard = FC<Props> {

    var currentPlayer by useState("x")
    var board by useState(
        arrayOf(
            arrayOf(" ", " ", " "),
            arrayOf(" ", " ", " "),
            arrayOf(" ", " ", " "),
        )
    )
    useEffect {
        println(currentPlayer)
        println(TicTacToeGameLogicUtils.getBestMove(board, currentPlayer))
    }

    table {
        css {
            width = 500.px
            height = 500.px
            backgroundColor = NamedColor.cyan
            position = Position.fixed
            margin = Margin((0).px, 0.px, 0.px, (0).px)
            textAlign = TextAlign.center
            border = Border(4.px, LineStyle.solid, NamedColor.white)
            borderCollapse = BorderCollapse.collapse
        }
        for (i in board.indices) {
            tr {
                for (j in board[i].indices) {
                    td {
                        css {
                            border = Border(4.px, LineStyle.solid, NamedColor.white)
                            borderCollapse = BorderCollapse.collapse
                            fontSize = 21.px
                        }
                        img {
                            css {
                                width = 160.px
                                height = 160.px
                            }
                            val s = when (board[i][j]) {
                                "x" -> "x.png"
                                "o" -> "o.png"
                                else -> ""
                            }
                            if (s != "") {
                                src = s
                            }
                            onClick = {
                                if (board[i][j].isBlank()) {
                                    val newBoard = board.copyOf().apply { this@apply[i][j] = currentPlayer }
                                    board = newBoard
                                    currentPlayer = if (currentPlayer == "x") "o" else "x"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


