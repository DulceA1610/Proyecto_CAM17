package com.example.cam17

import android.app.Application
import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems
    private val sharedPreferences = application.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        val cartJson = sharedPreferences.getString("cart_items", null)
        if (cartJson != null) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            _cartItems.value = gson.fromJson(cartJson, type)
        } else {
            _cartItems.value = emptyList()
        }
    }

    private fun saveCartItems() {
        val editor = sharedPreferences.edit()
        val cartJson = gson.toJson(_cartItems.value)
        editor.putString("cart_items", cartJson)
        editor.apply()
    }

    fun addToCart(item: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val existingItem = currentItems.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentItems.add(item)
        }
        _cartItems.value = currentItems
        saveCartItems()
    }

    fun removeFromCart(productName: String) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val item = currentItems.find { it.name == productName }
        if (item != null) {
            if (item.quantity > 1) {
                item.quantity--
            } else {
                currentItems.remove(item)
            }
        }
        _cartItems.value = currentItems
        saveCartItems()
    }

    fun getQuantity(productName: String): Int {
        return _cartItems.value?.find { it.name == productName }?.quantity ?: 0
    }

    fun getCartItems(): List<CartItem> {
        return _cartItems.value ?: emptyList()
    }

    fun getTotal(): Double {
        return _cartItems.value?.sumByDouble { it.price * it.quantity } ?: 0.0
    }
}

@Parcelize
data class CartItem(
    val name: String,
    val price: Double,
    val imageResId: Int,
    var quantity: Int = 1,
    var isSelected: Boolean = false
) : Parcelable
