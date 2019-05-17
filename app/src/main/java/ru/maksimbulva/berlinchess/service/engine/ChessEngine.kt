package ru.maksimbulva.berlinchess.service.engine

import chess.Move
import chess.Piece
import guibase.ChessController
import io.reactivex.Flowable
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.PieceOnBoard
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.service.engine.adapter.PieceTypeAdapter

typealias MyMove = ru.maksimbulva.berlinchess.model.chess.Move

class ChessEngine {

    private val listener = EngineListener()
    private val controller = ChessController(listener)

    val boardFlowable: Flowable<Board>
        get() = listener.positionFlowable
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

    fun playMove(move: MyMove) {
        controller.humanMove(Move(move.squareFrom.index, move.squareTo.index, Piece.EMPTY))
    }
}
