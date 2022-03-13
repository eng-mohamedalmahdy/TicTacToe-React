package view

import kotlin.math.*
import kotlin.experimental.and

object TicTacToeGameLogicUtils {
    private val winCombinations = shortArrayOf(7, 56, 448, 292, 146, 73, 273, 84)

    private fun isWinner(X: Short): Boolean {
        for (i in 0..7) if (X and winCombinations[i] == winCombinations[i]) return true
        return false
    }

    fun getWinner(board: Array<Array<String>>): String {
        val stringBitsX =
            board.fold("") { rowAcc, row -> "$rowAcc${row.fold("") { cellAcc, bit -> if (bit == "x") "${cellAcc}1" else "${cellAcc}0" }}" }
        val binaryRepX = stringBitsX.toShort(2)
        val stringBitsO =
            board.fold("") { rowAcc, row -> "$rowAcc${row.fold("") { cellAcc, bit -> if (bit == "o") "${cellAcc}1" else "${cellAcc}0" }}" }
        val binaryRepO = stringBitsO.toShort(2)
        return if (isWinner(binaryRepX)) "x" else if (isWinner(binaryRepO)) "o" else " "
    }
}

private fun Int.pow(i: Int): Int = this.toDouble().pow(i).toInt()
