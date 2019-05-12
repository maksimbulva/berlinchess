package ru.maksimbulva.berlinchess.ui.chessboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import ru.maksimbulva.berlinchess.R
import ru.maksimbulva.berlinchess.model.chess.PieceType
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.SquareColor

class ChessboardSquareView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val backgroundImage: ImageView
    private val pieceImage: ImageView
    @DrawableRes private val pieceResId: Int? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.chessboard_square, this, true)

        backgroundImage = findViewById(R.id.background)
        pieceImage = findViewById(R.id.piece)
    }

    fun setSquareColor(color: SquareColor) {
        @DrawableRes val backgroundResId = when (color) {
            SquareColor.Black -> R.drawable.fritz_d
            SquareColor.White -> R.drawable.fritz_l
        }
        backgroundImage.setImageResource(backgroundResId)
    }

    fun setItem(item: ChessboardItem) {
        @DrawableRes val pieceResId = getPieceResId(item)
        if (pieceResId == this.pieceResId) {
            return
        }

        if (pieceResId != null) {
            pieceImage.setImageResource(pieceResId)
            pieceImage.visibility = View.VISIBLE
        } else {
            pieceImage.visibility = View.GONE
        }
    }

    companion object {

        @DrawableRes
        private fun getPieceResId(item: ChessboardItem): Int? {
            val player = item.player ?: return null
            return when (item.pieceType) {
                PieceType.Pawn -> if (player == Player.Black) {
                    R.drawable.piece_set_alpha_black_pawn
                } else {
                    R.drawable.piece_set_alpha_white_pawn
                }
                PieceType.Knight -> if (player == Player.Black) {
                    R.drawable.piece_set_alpha_black_knight
                } else {
                    R.drawable.piece_set_alpha_white_knight
                }
                PieceType.Bishop -> if (player == Player.Black) {
                    R.drawable.piece_set_alpha_black_bishop
                } else {
                    R.drawable.piece_set_alpha_white_bishop
                }
                PieceType.Rook -> if (player == Player.Black) {
                    R.drawable.piece_set_alpha_black_rook
                } else {
                    R.drawable.piece_set_alpha_white_rook
                }
                PieceType.Queen -> if (player == Player.Black) {
                    R.drawable.piece_set_alpha_black_queen
                } else {
                    R.drawable.piece_set_alpha_white_queen
                }
                PieceType.King -> if (player == Player.Black) {
                    R.drawable.piece_set_alpha_black_king
                } else {
                    R.drawable.piece_set_alpha_white_king
                }
                null -> return null
            }
        }
    }
}
