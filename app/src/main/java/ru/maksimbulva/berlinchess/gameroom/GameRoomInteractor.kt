package ru.maksimbulva.berlinchess.gameroom

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Move
import ru.maksimbulva.berlinchess.model.chess.Position
import ru.maksimbulva.berlinchess.service.engine.ChessEngine

class GameRoomInteractor {

    private val chessEngine = ChessEngine()

    private val positionSubject: BehaviorSubject<Position> = BehaviorSubject.create()
    val positionFlowable: Flowable<Position> = positionSubject.toFlowable(BackpressureStrategy.LATEST)

    private val positionDisposable = chessEngine.positionFlowable
        .subscribe(positionSubject::onNext)

    val currentPosition: Position? get() = positionSubject.value

    val thinkingTextFlowable: Flowable<String> get() = chessEngine.thinkingTextFlowable

    fun playMove(move: Move) {
        chessEngine.playMove(move)
    }
}
