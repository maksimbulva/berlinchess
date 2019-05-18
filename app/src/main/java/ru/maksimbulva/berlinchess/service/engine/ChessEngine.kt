package ru.maksimbulva.berlinchess.service.engine

import chess.Move
import chess.Piece
import guibase.ChessController
import io.reactivex.Flowable

typealias MyMove = ru.maksimbulva.berlinchess.model.chess.Move

class ChessEngine {

    private val listener = EngineListener()
    private val controller = ChessController(listener)

    val positionFlowable = listener.positionFlowable

    val thinkingTextFlowable: Flowable<String> get() = listener.thinkingTextFlowable

    init {
        controller.newGame(true, 16, false)
        controller.startGame()
    }

    fun playMove(move: MyMove) {
        controller.humanMove(Move(move.squareFrom.index, move.squareTo.index, Piece.EMPTY))
    }
}
