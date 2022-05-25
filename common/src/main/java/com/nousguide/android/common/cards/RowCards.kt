package com.nousguide.android.common.cards

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.nousguide.android.common.R

class RowCards(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var title: TextView
    private var rowCards: LinearLayoutCompat

    init {
        inflate(context, R.layout.row_cards_layout, this)
        title = findViewById(R.id.tvCardTitle)
        rowCards = findViewById(R.id.rowView)
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun addCard(card: Card) {
        rowCards.addView(card)
    }

    fun addCards(list: List<Card>) {
        list.forEach { rowCards.addView(it) }
    }
}