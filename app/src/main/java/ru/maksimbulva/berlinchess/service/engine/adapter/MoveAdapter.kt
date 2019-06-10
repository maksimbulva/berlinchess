package ru.maksimbulva.berlinchess.service.engine.adapter

import chess.Piece
import ru.maksimbulva.berlinchess.model.chess.Move
import ru.maksimbulva.berlinchess.model.chess.PieceType
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square

object MoveAdapter {
    fun fromEngine(move: chess.Move): Move {
        return Move(
            squareFrom = Square(move.from),
            squareTo = Square(move.to),
            promoteTo = PieceTypeAdapter.fromEngine(move.promoteTo)?.second
        )
    }

    fun toEngine(move: Move, player: Player): chess.Move {
        return chess.Move(
            move.squareFrom.index,
            move.squareTo.index,
            promoteTo(move.promoteTo, player)
        )
    }

    private fun promoteTo(pieceType: PieceType?, player: Player): Int {
        return if (pieceType == null) {
            Piece.EMPTY
        } else {
            PieceTypeAdapter.toEngine(pieceType, player)
        }
    }
}
