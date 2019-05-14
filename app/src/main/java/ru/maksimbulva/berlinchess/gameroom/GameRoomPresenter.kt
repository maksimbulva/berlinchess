package ru.maksimbulva.berlinchess.gameroom

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem

class GameRoomPresenter(private val view: GameRoomContract.View) : GameRoomContract.Presenter {

    private var chessboardClicksDisposable: Disposable? = null

    private var chessboardItemDisposable: Disposable? = null

    private val selectedSquaresSubject: BehaviorSubject<Set<Int>> = BehaviorSubject.create()
    private var selectedSquaresDisposable: Disposable? = null

    private lateinit var interactor: GameRoomInteractor

    init {
        view.presenter = this
        selectedSquaresSubject.onNext(emptySet())
    }

    override fun start() {

        interactor = GameRoomInteractor()

        chessboardClicksDisposable = view.chessboardClicks
            .subscribeBy(
                onNext = { item ->
                    val currentSelection: Set<Int> = selectedSquaresSubject.value ?: emptySet()
                    selectedSquaresSubject.onNext(currentSelection + item.square.index)
                }
            )

        chessboardItemDisposable = Flowable.combineLatest<Board, Set<Int>, Pair<Board, Set<Int>>>(
            interactor.boardFlowable,
            selectedSquaresSubject.toFlowable(BackpressureStrategy.LATEST),
            BiFunction { board, selection -> board to selection }
        )
            .subscribeBy(
                onNext = { (board, selection) ->
                    val chessboardItems = board.squares.mapIndexed { index, pieceOnBoard ->
                        ChessboardItem(
                            square = Square(index),
                            player = pieceOnBoard?.player,
                            pieceType = pieceOnBoard?.pieceType,
                            isHighlighted = (index in selection)
                        )
                    }
                    view.setChessboardItems(chessboardItems)
                }
            )

        selectedSquaresDisposable = selectedSquaresSubject
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
    }
}
