package com.example.shoppinglist.domain

interface ShopListRepository {
    fun getShopList(): List<ShopItem>
    fun getShopItem(shopItemId: Int): ShopItem
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun addShopItem(shopItem: ShopItem)
}