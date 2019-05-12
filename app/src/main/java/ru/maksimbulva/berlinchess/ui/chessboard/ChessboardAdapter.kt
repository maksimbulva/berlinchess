package ru.maksimbulva.berlinchess.ui.chessboard

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.DrawableRes
import androidx.core.util.getOrDefault
import ru.maksimbulva.berlinchess.R
import ru.maksimbulva.berlinchess.model.chess.PieceType
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square

class ChessboardAdapter(private val context: Context) : BaseAdapter() {

    private val items = SparseArray<ChessboardItem>()

    override fun getCount() = SQUARE_COUNT

    override fun getItem(position: Int): Any = items.getOrDefault(position, ChessboardEmptyItem)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView != null && convertView is ChessboardSquareView) {
            convertView
        } else {
            ChessboardSquareView(context)
        }

        if ((position / 8 + position % 8) % 2 == 0) {
            view.setChessboardSquareImage(R.drawable.fritz_l)
        } else {
            view.setChessboardSquareImage(R.drawable.fritz_d)
        }

        val item = getItem(position)
        when (item) {
            is ChessboardPieceItem -> {
                @DrawableRes val pieceResId: Int = when (item.pieceType) {
                    PieceType.Pawn -> if (item.player == Player.Black) {
                        R.drawable.piece_set_alpha_black_pawn
                    } else {
                        R.drawable.piece_set_alpha_white_pawn
                    }
                    PieceType.Knight -> if (item.player == Player.Black) {
                        R.drawable.piece_set_alpha_black_knight
                    } else {
                        R.drawable.piece_set_alpha_white_knight
                    }
                    PieceType.Bishop -> if (item.player == Player.Black) {
                        R.drawable.piece_set_alpha_black_bishop
                    } else {
                        R.drawable.piece_set_alpha_white_bishop
                    }
                    PieceType.Rook -> if (item.player == Player.Black) {
                        R.drawable.piece_set_alpha_black_rook
                    } else {
                        R.drawable.piece_set_alpha_white_rook
                    }
                    PieceType.Queen -> if (item.player == Player.Black) {
                        R.drawable.piece_set_alpha_black_queen
                    } else {
                        R.drawable.piece_set_alpha_white_queen
                    }
                    PieceType.King -> if (item.player == Player.Black) {
                        R.drawable.piece_set_alpha_black_king
                    } else {
                        R.drawable.piece_set_alpha_white_king
                    }
                }
                view.setPieceImage(pieceResId)
            }
        }

        return view
    }

    fun setItems(newItems: Collection<ChessboardItem>) {
        items.clear()
        newItems.forEach {
            when (it) {
                is ChessboardPieceItem -> {
                    val position = squareToPosition(it.square)
                    items.append(position, it)
                }
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