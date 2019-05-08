package ru.maksimbulva.berlinchess.ui.chessboard

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import ru.maksimbulva.berlinchess.R

class ChessboardAdapter(private val context: Context) : BaseAdapter() {

    override fun getCount() = 64

    override fun getItem(position: Int): Any {
        return ChessboardItem()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = if (convertView == null || convertView !is ImageView) {
            ImageView(context).apply {
                adjustViewBounds = true
            }
        } else {
            convertView
        }
        if ((position / 8 + position % 8) % 2 == 0) {
            imageView.setImageResource(R.drawable.fritz_l)
        } else {
            imageView.setImageResource(R.drawable.fritz_d)
        }
        return imageView
    }
}