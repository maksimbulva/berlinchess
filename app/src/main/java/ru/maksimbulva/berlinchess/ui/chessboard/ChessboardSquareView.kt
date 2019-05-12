package ru.maksimbulva.berlinchess.ui.chessboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import ru.maksimbulva.berlinchess.R

class ChessboardSquareView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    val backgroundImage: ImageView
    val pieceImage: ImageView

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.chessboard_square, this, true)

        backgroundImage = findViewById(R.id.background)
        pieceImage = findViewById(R.id.piece)
    }

    fun setChessboardSquareImage(@DrawableRes squareResId: Int) {
        backgroundImage.setImageResource(squareResId)
    }

    fun setPieceImage(@DrawableRes pieceResId: Int) {
        pieceImage.setImageResource(pieceResId)
    }
}
