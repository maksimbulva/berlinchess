package ru.maksimbulva.berlinchess.gameroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.rxkotlin.subscribeBy
import ru.maksimbulva.berlinchess.R
import ru.maksimbulva.berlinchess.mvvm.RxFragment
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardView
import ru.maksimbulva.berlinchess.ui.promotion.PiecePromotionView

class GameRoomFragment : RxFragment() {

    private lateinit var chessboard: ChessboardView
    private lateinit var piecePromotionSelector: PiecePromotionView

    private lateinit var model: GameRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(GameRoomViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        model.chessboardItems.observe(this, Observer<Collection<ChessboardItem>> { items ->
            setChessboardItems(items)
        })

        model.piecePromotionRequest.observe(this, Observer {
            chessboard.isEnabled = false
            piecePromotionSelector.visibility = View.VISIBLE
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.gameroom_fragment, container, false)

        chessboard = root.findViewById(R.id.chessboard)
        addSubscription(
            chessboard.clicks.subscribeBy(onNext = model::addUserSelectedItem)
        )

        piecePromotionSelector = root.findViewById(R.id.piece_promotion_selector)
        addSubscription(
            piecePromotionSelector.pieceSelectedFlowable.subscribeBy(
                onNext = { pieceType ->
                    chessboard.isEnabled = true
                    piecePromotionSelector.visibility = View.GONE
                    model.onPromotionPieceSelected(pieceType)
                }
            )
        )

        return root
    }

    private fun setChessboardItems(items: Collection<ChessboardItem>) {
        chessboard.setItems(items)
    }
}
