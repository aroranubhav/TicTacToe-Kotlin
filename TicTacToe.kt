package TicTacToeKotlin

import TicTacToeKotlin.TicTacToe.Companion.COMPUTER
import TicTacToeKotlin.TicTacToe.Companion.PLAYER
import TicTacToeKotlin.TicTacToe.Companion.computerPositions
import TicTacToeKotlin.TicTacToe.Companion.gameBoard
import TicTacToeKotlin.TicTacToe.Companion.playerPositions
import TicTacToeKotlin.TicTacToe.Companion.setUpGameBoard
import TicTacToeKotlin.TicTacToe.Companion.winningPositions
import kotlin.system.exitProcess

fun main() {
    setUpGameBoard()
    playGame()
}

class TicTacToe {

    companion object {
        const val PLAYER = "player"
        const val COMPUTER = "computer"
        lateinit var gameBoard: Array<CharArray>
        var playerPositions: MutableList<Int> = mutableListOf()
        var computerPositions: MutableList<Int> = mutableListOf()
        val winningPositions = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9),
                listOf(1, 4, 7),
                listOf(2, 5, 8),
                listOf(3, 6, 9),
                listOf(1, 5, 9),
                listOf(3, 5, 7)
        )

        fun setUpGameBoard() {
            gameBoard = arrayOf(
                    charArrayOf(' ', '|', ' ', '|', ' '),
                    charArrayOf('-', '+', '-', '+', '-'),
                    charArrayOf(' ', '|', ' ', '|', ' '),
                    charArrayOf('-', '+', '-', '+', '-'),
                    charArrayOf(' ', '|', ' ', '|', ' ')
            )
        }
    }
}


fun playGame() {
    val playersTurn = (1..2).random()
    val firstMove = if (playersTurn == 1) {
        playersTurn()
        'P'
    } else {
        computersTurn()
        'C'
    }

    while (true) {
        if (firstMove == 'P') {
            computersTurn()
            playersTurn()
        } else {
            playersTurn()
            computersTurn()
        }
    }
}

fun playersTurn() {
    println("Enter your position : (1-9)")
    var position: Int? = readLine()?.toInt()
    while (position !in (1..9) || playerPositions.contains(position) || computerPositions.contains(position)) {
        println("Please enter a valid position:")
        position = readLine()?.toInt()
    }
    position?.let {
        placePiece(position, PLAYER)
    }
    printGameBoard(PLAYER)

    val result = isGameFinished()
    if (result != -1) {
        printResult(result)
    }

}


fun computersTurn() {
    var position = (1..9).random()
    while (playerPositions.contains(position) || computerPositions.contains(position)) {
        position = (1..9).random()
    }
    placePiece(position, COMPUTER)
    printGameBoard(COMPUTER)
    val result = isGameFinished()
    if (result != -1) {
        printResult(result)
    }
}

fun placePiece(position: Int, user: String) {
    val symbol = if (user.equals(PLAYER, true)) {
        playerPositions.add(position)
        'X'
    } else {
        computerPositions.add(position)
        'O'
    }

    when (position) {
        1 -> gameBoard[0][0] = symbol
        2 -> gameBoard[0][2] = symbol
        3 -> gameBoard[0][4] = symbol
        4 -> gameBoard[2][0] = symbol
        5 -> gameBoard[2][2] = symbol
        6 -> gameBoard[2][4] = symbol
        7 -> gameBoard[4][0] = symbol
        8 -> gameBoard[4][2] = symbol
        9 -> gameBoard[4][4] = symbol
    }
}

fun isGameFinished(): Int {
    for (positions in winningPositions) {
        if (playerPositions.containsAll(positions)) {
            return 1
        } else if (computerPositions.containsAll(positions)) {
            return 2
        }
    }
    if (playerPositions.size + computerPositions.size == 9) {
        return 0
    }
    return -1
}

fun printResult(result: Int) {
    when (result) {
        1 -> println("Yay, you win !!")
        2 -> println("Shoot !! You loose :(")
        0 -> println("It's a tie :)")
    }

    println("Do you want to play again ? Y / N")
    val input = readLine()
    input?.let {
        if (input.equals("Y", ignoreCase = true)) {
            resetGameBoard()
        } else {
            exitProcess(0)
        }
    }
}

fun resetGameBoard() {
    setUpGameBoard()
    playerPositions.clear()
    computerPositions.clear()
}

fun printGameBoard(input: String) {

    if (input == PLAYER) {
        println("Player's Turn")
    } else {
        println("Computer's Turn")
    }

    println()

    for (boardRow in gameBoard) {
        for (element in boardRow) {
            print(element)
        }
        println()
    }

    println()
    println("-+-+-+-+-+-+-")
    println()
}
