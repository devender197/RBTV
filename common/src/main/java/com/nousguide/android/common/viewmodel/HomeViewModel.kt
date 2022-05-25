package com.nousguide.android.common.viewmodel

import androidx.lifecycle.viewModelScope
import com.nousguide.android.common.base.BaseViewModel
import com.nousguide.android.common.cards.Card
import com.nousguide.android.common.cards.CardFactory
import com.nousguide.android.common.commom.CommonLiveEvent
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.manager.BackendManager
import com.nousguide.android.common.manager.LogManager
import com.nousguide.android.common.models.core.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardFactory: CardFactory,
    private val backendManager: BackendManager
) : BaseViewModel(), CardClickListener {

    private var programs: List<ProductModel> = listOf()

    val eventShowHud = CommonLiveEvent<Boolean>()
    val eventAddCardRows = CommonLiveEvent<List<Pair<String, List<Card>>>>()
    val eventAddHeaderRow = CommonLiveEvent<Card>()
    val eventLoadHeaderItem = CommonLiveEvent<ProductModel>()

    fun loadPrograms() {
        getProduct()
    }

    private fun getProduct() {
        viewModelScope.launch {
            eventShowHud.postValue(true)
            val channels = backendManager.getLinearChannel()
            val continueWatching = backendManager.getContinueWatching()
            LogManager.log(continueWatching.toString())
            val product = backendManager.getProduct("discover")

            val browserModels = product?.collections?.map { it ->
                ProductModel(it.id, it.label)
            }
            browserModels?.forEach { model ->
                backendManager.getCollectionForProductId(model.id)?.let { collections ->
                    collections.getItems?.let {
                        model.productCollection = it
                    }
                }
            }
            browserModels?.filter { it.productCollection != null }?.toMutableList()?.let { list ->
                channels?.let { channel ->
                    if (list.size > 2)
                        list.add(2, ProductModel(channel.id, channel.label, channel.items))
                }
                programs = list
                eventLoadHeaderItem.postValue(programs.first())
                createCard()
            }
            eventShowHud.postValue(false)
        }
    }

    private fun createCard() {
        val cardRows: MutableList<Pair<String, List<Card>>> = mutableListOf()

        programs.forEach { model ->
            val listCard: MutableList<Card> = mutableListOf()
            model.productCollection?.forEach { item ->
                listCard.add(cardFactory.createCard(item))
            }
            cardRows.add(Pair(model.title, listCard))
        }

        eventAddHeaderRow.postValue(cardFactory.createHeaderCard(programs.first()))
        eventAddCardRows.postValue(cardRows)
    }

    override fun onItemClickListener(item: Any) {}
}