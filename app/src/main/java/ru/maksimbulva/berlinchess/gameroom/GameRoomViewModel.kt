package ru.maksimbulva.berlinchess.gameroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.Position
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.mvvm.RxViewModel
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem

class GameRoomViewModel : RxViewModel() {

    private val interactor = GameRoomInteractor()

    private val moveInputInteractor = MoveInputInteractor()

    private val _chessboardItems = MutableLiveData<Collection<ChessboardItem>>()

    val chessboardItems: LiveData<Collection<ChessboardItem>> = _chessboardItems

    private val _thinkingTextFlowable = MutableLiveData<String>()

    val thinkingTextFlowable: LiveData<String> = _thinkingTextFlowable

    init {
        addSubscription(
            Flowable.combineLatest<Position, List<Square>, Pair<Position, List<Square>>>(
                interactor.positionFlowable,
                moveInputInteractor.selectionFlowable,
                BiFunction { position, selection -> position to selection }
            )
                .subscribeBy(
                    onNext = { (position, selection) ->
                        _chessboardItems.value = position.board.squares.mapIndexed { index, pieceOnBoard ->
                            ChessboardItem(
                                square = Square(index),
                                player = pieceOnBoard?.player,
                                pieceType = pieceOnBoard?.pieceType,
                                isHighlighted = (Square(index) in selection)
                            )
                        }
                    }
                )
        )

        addSubscription(
            moveInputInteractor.moveFlowable
                .subscribeBy(
                    onNext = { move -> interactor.playMove(move) }
                )
        )
    }

    fun addUserSelectedItem(item: ChessboardItem) {
        val board = interactor.currentPosition?.board ?: return
        moveInputInteractor.onSquareSelected(item.square, board)
    }
}
