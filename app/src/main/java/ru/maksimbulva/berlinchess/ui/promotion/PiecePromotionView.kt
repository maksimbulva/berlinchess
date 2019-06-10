package ru.maksimbulva.berlinchess.ui.promotion

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageButton
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import ru.maksimbulva.berlinchess.R
import ru.maksimbulva.berlinchess.model.chess.PieceType

class PiecePromotionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val cancelButton: ImageButton
    private val pieceButtons: Map<ImageButton, PieceType>

    private val pieceSelectedSubject: PublishSubject<PieceType> = PublishSubject.create()

    val pieceSelectedFlowable: Flowable<PieceType>
        get() = pieceSelectedSubject.toFlowable(BackpressureStrategy.LATEST)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.piece_promotion, this, true)

        cancelButton = findViewById(R.id.cancel_promotion)
        cancelButton.setOnClickListener { pieceSelectedSubject.onNext(PieceType.Pawn) }

        pieceButtons = hashMapOf(
            Pair(findViewById(R.id.promote_to_knight), PieceType.Knight),
            Pair(findViewById(R.id.promote_to_bishop), PieceType.Bishop),
            Pair(findViewById(R.id.promote_to_rook), PieceType.Rook),
            Pair(findViewById(R.id.promote_to_queen), PieceType.Queen)
        )

        pieceButtons.keys.asSequence()
            .forEach { imageButton ->
                imageButton.setOnClickListener {
                    pieceSelectedSubject.onNext(pieceButtons[imageButton] ?: PieceType.Pawn)
                }
            }
    }
}