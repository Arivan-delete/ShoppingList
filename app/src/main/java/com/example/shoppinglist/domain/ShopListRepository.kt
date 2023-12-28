package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun getShopItem(shopItemId: Int): ShopItem
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun addShopItem(shopItem: ShopItem)
}