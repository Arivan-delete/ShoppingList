package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val getShopItemUseCase: GetShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputAmount = MutableLiveData<Boolean>()
    val errorInputAmount: LiveData<Boolean>
        get() = _errorInputAmount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = item
        }
    }

    fun addShopItem(inputName: String?, inputAmount: String?) {
        val name = parseName(inputName)
        val amount = parseAmount(inputAmount)
        val fieldsValid = validateInput(name, amount)
        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, amount, true)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputAmount: String?) {
        val name = parseName(inputName)
        val amount = parseAmount(inputAmount)
        val fieldsValid = validateInput(name, amount)
        if (fieldsValid) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, amount = amount)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseAmount(inputAmount: String?): Int {
        return try {
            inputAmount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, amount: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (amount <= 0) {
            _errorInputAmount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputAmount() {
        _errorInputAmount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}