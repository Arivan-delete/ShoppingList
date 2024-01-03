package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor(){

    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        amount = shopItem.amount,
        enabled = shopItem.enabled
    )
    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItem = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        amount = shopItemDbModel.amount,
        enabled = shopItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}