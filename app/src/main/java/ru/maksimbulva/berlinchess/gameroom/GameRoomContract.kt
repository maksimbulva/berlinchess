package ru.maksimbulva.berlinchess.gameroom

import io.reactivex.Flowable
import ru.maksimbulva.berlinchess.mvp.BasePresenter
import ru.maksimbulva.berlinchess.mvp.BaseView
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem

interface GameRoomContract {
    interface View : BaseView<Presenter> {
        val chessboardClicks: Flowable<ChessboardItem>
        fun setChessboardItems(items: Collection<ChessboardItem>)
    }

    interface Presenter : BasePresenter
}
