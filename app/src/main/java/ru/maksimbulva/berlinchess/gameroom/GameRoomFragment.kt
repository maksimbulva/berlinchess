package ru.maksimbulva.berlinchess.gameroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.rxkotlin.subscribeBy
import ru.maksimbulva.berlinchess.R
import ru.maksimbulva.berlinchess.mvvm.RxFragment
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardView
import java.lang.Exception

class GameRoomFragment : RxFragment() {

    private lateinit var chessboard: ChessboardView
    private lateinit var thinkingText: TextView

    private lateinit var model: GameRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(GameRoomViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        model.chessboardItems.observe(this, Observer<Collection<ChessboardItem>> { items ->
            setChessboardItems(items)
        })

        model.thinkingTextFlowable.observe(this, Observer<String> { text ->
            thinkingText.text = text
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.gameroom_fragment, container, false)

        chessboard = root.findViewById(R.id.chessboard)
        addSubscription(
            chessboard.clicks.subscribeBy(onNext = model::addUserSelectedItem)
        )

        thinkingText = root.findViewById(R.id.thinking_text)

        return root
    }

    private fun setChessboardItems(items: Collection<ChessboardItem>) {
        chessboard.setItems(items)
    }
}
