package ru.maksimbulva.berlinchess.gameroom

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.service.engine.ChessEngine

class GameRoomInteractor {

    private val chessEngine = ChessEngine()

    private val boardSubject: BehaviorSubject<Board> = BehaviorSubject.create()
    val boardFlowable: Flowable<Board> = boardSubject.toFlowable(BackpressureStrategy.LATEST)

    private val boardDisposable = chessEngine.boardFlowable
        .subscribe(boardSubject::onNext)

    val currentBoard: Board? get() = boardSubject.value

    fun playMove(moveFrom: Square, moveTo: Square) {
        chessEngine.playMove(moveFrom, moveTo)
    }
}
