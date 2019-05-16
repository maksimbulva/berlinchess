package ru.maksimbulva.berlinchess.ui.chessboard

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.util.containsKey
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.model.chess.SquareColor

class ChessboardAdapter(private val context: Context) : BaseAdapter() {

    private val items = SparseArray<ChessboardItem>()
    private val views = SparseArray<ChessboardSquareView>()

    private val clicksSubject = BehaviorSubject.create<ChessboardItem>()
    val clicks: Flowable<ChessboardItem> = clicksSubject.toFlowable(BackpressureStrategy.LATEST)

    override fun getCount() = SQUARE_COUNT

    override fun getItem(position: Int): Any {
        return if (items.containsKey(position)) {
            items[position]
        } else {
            Any()
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (views.containsKey(position)) {
            return views[position]
        }

        val view = ChessboardSquareView(context)
        views.append(position, view)

        val squareColor = if ((position / 8 + position % 8) % 2 == 0) {
            SquareColor.White
        } else {
            SquareColor.Black
        }
        view.setSquareColor(squareColor)

        if (items.containsKey(position)) {
            view.setItem(items[position])
        }

        view.setOnClickListener { view.item?.let(clicksSubject::onNext) }

        return view
    }

    fun setItems(newItems: Collection<ChessboardItem>) {
        items.clear()
        newItems.forEach {
            val position = squareToPosition(it.square)
            items.append(position, it)
            if (views.containsKey(position)) {
                views[position].setItem(it)
            }
        }
    }

    companion object {
        private const val SQUARE_COUNT = 64

        fun squareToPosition(square: Square): Int {
            return Square(row = 7 - square.row, column = square.column).index
        }
    }
}