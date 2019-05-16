package ru.maksimbulva.berlinchess.gameroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.mvvm.RxViewModel
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem

class GameRoomViewModel : RxViewModel() {

    private val interactor = GameRoomInteractor()

    private val selectedSquaresSubject: BehaviorSubject<Set<Int>> = BehaviorSubject.create()

    private val _chessboardItems = MutableLiveData<Collection<ChessboardItem>>()

    val chessboardItems: LiveData<Collection<ChessboardItem>> = _chessboardItems

    init {
        addSubscription(
            Flowable.combineLatest<Board, Set<Int>, Pair<Board, Set<Int>>>(
                interactor.boardFlowable,
                selectedSquaresSubject.toFlowable(BackpressureStrategy.LATEST),
                BiFunction { board, selection -> board to selection }
            )
                .subscribeBy(
                    onNext = { (board, selection) ->
                        _chessboardItems.value = board.squares.mapIndexed { index, pieceOnBoard ->
                            ChessboardItem(
                                square = Square(index),
                                player = pieceOnBoard?.player,
                                pieceType = pieceOnBoard?.pieceType,
                                isHighlighted = (index in selection)
                            )
                        }
                    }
                )
        )

        addSubscription(
            selectedSquaresSubject
                .filter { it.size == 2 }
                .subscribeBy(
                    onNext = { selection ->
                        val board = interactor.currentBoard
                        if (board != null) {
                            val moveFrom = selection.find { board.squares[it]?.player == Player.White }
                            val moveTo = selection.find { it != moveFrom }
                            if (moveFrom != null && moveTo != null) {
                                interactor.playMove(Square(moveFrom), Square(moveTo))
                            }
                        }
                        selectedSquaresSubject.onNext(emptySet())
                    }
                )
        )

        selectedSquaresSubject.onNext(emptySet())
    }

    fun addUserSelectedItem(item: ChessboardItem) {
        val currentSelection: Set<Int> = selectedSquaresSubject.value ?: emptySet()
        selectedSquaresSubject.onNext(currentSelection + item.square.index)
    }
}
