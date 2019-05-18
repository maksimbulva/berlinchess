package ru.maksimbulva.berlinchess.gameroom

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.Move
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square

class MoveInputInteractor {

    private val selectionSubject: BehaviorSubject<List<Square>> = BehaviorSubject.create()

    val selectionFlowable: Flowable<List<Square>> get() = selectionSubject.toFlowable(BackpressureStrategy.LATEST)

    private val selection: List<Square> get() = selectionSubject.value ?: emptyList()

    private val moveSubject: BehaviorSubject<Move> = BehaviorSubject.create()

    val moveFlowable: Flowable<Move> get() = moveSubject.toFlowable(BackpressureStrategy.LATEST)

    init {
        selectionSubject.onNext(emptyList())
    }

    fun onSquareSelected(selectedSquare: Square, board: Board) {
        val pieceOnBoard = board.at(selectedSquare)
        if (selection.size == 1) {
            val squareFrom = selection.first()
            // TODO: check if move is valid
            selectionSubject.onNext(emptyList())
            val move = Move(squareFrom, selectedSquare)
            if (move.squareFrom != move.squareTo) {
                moveSubject.onNext(move)
            }
        } else {
            if (pieceOnBoard?.player == Player.White) {
                selectionSubject.onNext(listOf(selectedSquare))
            } else {
                selectionSubject.onNext(emptyList())
            }
        }
    }
}
