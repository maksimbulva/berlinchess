package ru.maksimbulva.berlinchess.service.engine.adapter

import ru.maksimbulva.berlinchess.model.chess.*

object PositionAdapter {
    fun fromEngine(position: chess.Position): Position {
        return Position(
            board = createBoard(position),
            playerToMove = if (position.whiteMove) Player.White else Player.Black
        )
    }

    private fun createBoard(position: chess.Position): Board {
        return Board(
            position.squares.asSequence()
                .mapIndexedNotNull { index, pieceOnSquare ->
                    val playerPiece = PieceTypeAdapter.fromEngine(pieceOnSquare)
                    playerPiece?.let { PieceOnBoard(it.first, it.second, Square(index)) }
                }
                .toList()
        )
    }
}
