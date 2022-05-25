package com.nousguide.android.rbtv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nousguide.android.common.commom.CommonRecyclerViewAdapter
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.rbtv.R
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.core.ProductModel
import kotlinx.coroutines.CoroutineScope


class HomeAdapter(
    private val context: Context,
    private val listener: CardClickListener,
    products: MutableList<ProductModel> = mutableListOf(),
    private val scopeProvider: () -> CoroutineScope,
) : CommonRecyclerViewAdapter(scopeProvider) {

    private val items: MutableList<ProductModel> = products
    private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun createCommonViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return HomeItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.horizontal_item_view, parent, false),
            scopeProvider,
            viewPool
        )
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        (holder as HomeItemViewHolder).setup(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    fun addItems(newItems: List<ProductModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateItem(item: ProductModel) {
        items.indexOfFirst { it.id == item.id }.let { index ->
            if (index > 0) {
                items[index].productCollection = item.productCollection
                notifyItemChanged(index, items[index])
            }
        }
    }

    fun addItem(item: ProductModel) {
        val isAdded = items.add(item)
        if (isAdded) notifyItemChanged(items.size - 1)
    }

}

