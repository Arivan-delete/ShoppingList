package com.example.shoppinglist.presentation

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
): ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }
    fun changeEnabledState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}