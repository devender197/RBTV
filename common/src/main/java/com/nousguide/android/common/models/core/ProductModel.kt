package com.nousguide.android.common.models.core

import com.nousguide.android.common.models.ProductCollection.Item

class ProductModel(
    val id: String = "",
    val title: String = "",
    var productCollection: List<Any>? = null,
    var isSkeleton: Boolean = false
) {
    companion object {
        fun getSkeleton(): List<ProductModel> {
            val list = mutableListOf<ProductModel>()
            list.add(
                ProductModel(
                    title = "Featuring Test Cases",
                    productCollection = Item.getSkeleton(),
                    isSkeleton = true
                )
            )
            repeat(5) {
                list.add(
                    ProductModel(
                        productCollection = Item.getSkeleton(),
                        isSkeleton = true
                    )
                )
            }
            return list
        }
    }
}