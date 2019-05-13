package ru.maksimbulva.berlinchess.service.engine

import chess.Move
import chess.Piece
import guibase.ChessController
import io.reactivex.Observable
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.PieceOnBoard
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.service.engine.adapter.PieceTypeAdapter

class ChessEngine {

    private val listener = EngineListener()
    private val controller = ChessController(listener)

    val boardObservable: Observable<Board>
        get() = listener.positionObservable
            .map { position ->
                val pieces = position.squares.asSequence()
                    .mapIndexedNotNull { index, pieceOnSquare ->
                        val playerPiece = PieceTypeAdapter.fromEngine(pieceOnSquare)
                        playerPiece?.let { PieceOnBoard(it.first, it.second, Square(index)) }
                    }
                Board(pieces.toList())
            }

    init {
        controller.newGame(true, 16, false)
        controller.startGame()
    }

    fun playMove(moveFrom: Square, moveTo: Square) {
        controller.humanMove(Move(moveFrom.index, moveTo.index, Piece.EMPTY))
    }
}
