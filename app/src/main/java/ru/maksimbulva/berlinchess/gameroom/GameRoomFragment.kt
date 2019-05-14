package ru.maksimbulva.berlinchess.gameroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import ru.maksimbulva.berlinchess.R
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardView

class GameRoomFragment : Fragment(), GameRoomContract.View {

    private lateinit var chessboard: ChessboardView

    override lateinit var presenter: GameRoomContract.Presenter

    override val chessboardClicks: Flowable<ChessboardItem>
        get() = chessboard.clicks.toFlowable(BackpressureStrategy.LATEST)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.gameroom_fragment, container, false)

        chessboard = root.findViewById(R.id.chessboard)

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setChessboardItems(items: Collection<ChessboardItem>) {
        chessboard.setItems(items)
    }
}
