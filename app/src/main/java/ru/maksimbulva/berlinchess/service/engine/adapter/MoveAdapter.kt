package ru.maksimbulva.berlinchess.service.engine.adapter

import ru.maksimbulva.berlinchess.model.chess.Move
import ru.maksimbulva.berlinchess.model.chess.Square

object MoveAdapter {
    fun fromEngine(move: chess.Move): Move {
        return Move(
            squareFrom = Square(move.from),
            squareTo = Square(move.to),
            promoteTo = PieceTypeAdapter.fromEngine(move.promoteTo)?.second
        )
    }
}
