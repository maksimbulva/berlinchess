package ru.maksimbulva.berlinchess.ui.chessboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.GridView
import ru.maksimbulva.berlinchess.R

class ChessboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val grid: GridView
    private val adapter = ChessboardAdapter(context)

    val clicks get() = adapter.clicks

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.chessboard, this, true)

        grid = findViewById(R.id.grid)
        grid.adapter = adapter
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minSize = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(minSize, minSize)
    }

    fun setItems(items: Collection<ChessboardItem>) {
        adapter.setItems(items)
    }
}
