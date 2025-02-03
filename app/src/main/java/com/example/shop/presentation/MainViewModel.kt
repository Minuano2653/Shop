package com.example.shop.presentation

import androidx.lifecycle.ViewModel
import com.example.shop.data.ShopListRepositoryImpl //presentation не должен зависеть от data
import com.example.shop.domain.DeleteShopItemUseCase
import com.example.shop.domain.EditShopItemUseCase
import com.example.shop.domain.GetShopListUseCase
import com.example.shop.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }

}