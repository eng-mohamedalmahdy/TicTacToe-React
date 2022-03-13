package view

import kotlin.math.*
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

object TicTacToeGameLogicUtils {
    private val winCombinations = shortArrayOf(7, 56, 448, 292, 146, 73, 273, 84)

    /*
    000 100 100 P1
    100 100 100 WC
    000 100 100 P1&WC
    100 000 000 (P1&WC)^WC

    011 000 000 P2

    011 100 100 E = P1 | P2
    100 011 011 !E
    000 001 001 P1W
    000 001 001 P1W & !E
     */
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

    fun getBestMove(board: Array<Array<String>>, currentPlayer: String): Pair<Int, Int> {

        val currentPlayerStringBits =
            board.fold("") { rowAcc, row -> "$rowAcc${row.fold("") { cellAcc, bit -> if (bit == currentPlayer) "${cellAcc}1" else "${cellAcc}0" }}" }
        val currentPlayerDecRep = currentPlayerStringBits.toShort(2)

        val otherPlayer = if (currentPlayer == "x") "o" else "x"

        val otherPlayerStringBits =
            board.fold("") { rowAcc, row -> "$rowAcc${row.fold("") { cellAcc, bit -> if (bit == otherPlayer) "${cellAcc}1" else "${cellAcc}0" }}" }

        val otherPlayerDecRep = otherPlayerStringBits.toShort(2)


        val emptyCells = currentPlayerDecRep.or(otherPlayerDecRep).inv().and((1 shl 9) - 1)
        var minStepCount = 100
        var minComp: Short = -1

        println("""
            P1:${currentPlayer}     P2:${otherPlayer}
            P1:${currentPlayerDecRep.toString(2).take(9)}   p2:${otherPlayerDecRep.toString(2).take(9)}
            EB:${emptyCells.toString(2).take(9)}
        """.trimIndent())

        winCombinations.forEachIndexed { idx, winComb ->
            val lookahead = (currentPlayerDecRep and winComb) xor winComb
            val validLookAhead = lookahead and emptyCells
            if (areCellsEmpty(lookahead.toInt(), emptyCells.toInt())) {
                if (validLookAhead.countOneBits() < minStepCount) {
                    print("${lookahead.toString(2).take(9)} ")
                    minStepCount = validLookAhead.countOneBits()
                    minComp = lookahead
                }
            }
        }
        val bestIdx = minComp.toString(2).padStart(9, '0').indexOfFirst { it == '1' }
        println(bestIdx)
        val i = bestIdx / 3
        val j = bestIdx % 3
        return Pair(i, j)
    }
}

private fun areCellsEmpty(lookAhead: Int, emptyCells: Int): Boolean {
    var lookAhead: Int = lookAhead
    var emptyCells: Int = emptyCells
    while (lookAhead != 0) {
        if (lookAhead % 2 == 1) {
            if (emptyCells % 2 != 1) return false
        }
        lookAhead /= 2
        emptyCells /= 2
    }
    return true
}

private fun Int.pow(i: Int): Int = this.toDouble().pow(i).toInt()
private fun Short.log2(): Int = log2(this.toDouble()).toInt()
