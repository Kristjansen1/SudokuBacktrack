private const val ROWS = 9
private const val COLUMNS = 9

private val sudoku = mutableListOf(
    0,0,0, 3,0,0, 2,0,0,
    0,0,0, 0,0,8, 0,0,0,
    0,7,8, 0,6,0, 3,4,0,

    0,4,2, 5,1,0, 0,0,0,
    1,0,6, 0,0,0, 4,0,9,
    0,0,0, 0,8,6, 1,5,0,

    0,3,5, 0,9,0, 7,6,0,
    0,0,0, 7,0,0, 0,0,0,
    0,0,9, 0,0,5, 0,0,0)
private var sudokuBoard = Array(ROWS) { IntArray(COLUMNS) }

fun main() {
    fillBoard()
    solveBoard()

}

fun solveBoard() {
    //lol
    var xAxis = 0
    var yAxis = 0
    if (isBoardFull()) {
        println("printing")
        printBoard()
    } else {
        //find first empty space
        for (x in 0..8) {
            for (y in 0..8) {
                if (sudokuBoard[x][y] == 0) {
                    xAxis = x
                    yAxis = y
                    break
                }
            }
        }
        val map = possibleEntries(sudokuBoard, xAxis, yAxis)
        //println(map)
        for (i in 1..9) {
            if (map[i] != 0) {
                sudokuBoard[xAxis][yAxis] = map[i]!!
               /* println("************************")
                println("value: ${sudokuBoard[xAxis][yAxis]}")
                println("$xAxis, $yAxis")
                println("map: $map")
                printBoard()*/
                solveBoard()
            }
        }
        //println("backtracking")
        sudokuBoard[xAxis][yAxis] = 0
    }
}

fun possibleEntries(board: Array<IntArray>, xAxis: Int, yAxis: Int): HashMap<Int, Int> {
    val map = hashMapOf<Int, Int>()
    for (x in 1..9) {
        map[x] = 0
    }
    for (y in 0..8) {
        if (board[xAxis][y] != 0) {
            map[board[xAxis][y]] = 1
        }
    }
    for (x in 0..8) {
        if (board[x][yAxis] != 0) {
            map[board[x][yAxis]] = 1
        }
    }
    val k = when (xAxis) {
        in 0..2 -> 0
        in 3..5 -> 3
        else -> 6
    }
    val l = when (yAxis) {
        in 0..2 -> 0
        in 3..5 -> 3
        else -> 6
    }

    for (x in k..k + 2) {
        for (y in l..l + 2) {
            //println("x: $x, y: $y")
            if (sudokuBoard[x][y] != 0) {
                map[sudokuBoard[x][y]] = 1
                //println("map: ${map[sudokuBoard[x][y]]}, sb: ${sudokuBoard[x][y]}")
            }
        }
    }
    for (x in 1..9) {
        if (map[x] == 0) {
            map[x] = x
            //println("map:")
        } else {
            map[x] = 0
        }
    }
    return map
}

fun fillBoard() {
    var listCursor = 0
    for (x in 0..8) {
        for (y in 0..8) {
            sudokuBoard[x][y] = sudoku[listCursor]
            listCursor++
        }
    }
    printBoard()
}

fun printBoard() {
    for (x in 0..8) {
        for (y in 0..8) {
            print("${sudokuBoard[x][y]} ")
        }
        println()
    }
}

fun isBoardFull(): Boolean {
    for (x in 0..8) {
        for (y in 0..8) {
            if (sudokuBoard[x][y] == 0) {
                return false
            }
        }
    }
    return true
}
