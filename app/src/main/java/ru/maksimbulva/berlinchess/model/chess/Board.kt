package ru.maksimbulva.berlinchess.model.chess

class Board(val pieces: Collection<PieceOnBoard>) {
    val squares = Array<PieceOnBoard?>(size = 64) { null }

    init {
        pieces.forEach {
            squares[it.square.index] = it
        }
    }
}
