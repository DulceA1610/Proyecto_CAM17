package com.example.cam17

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReposteriaActivity : BaseActivity() {
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContent(R.layout.activity_reposteria)

        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        setupBottomNavigation()
        setupProductButtons()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.menu.setGroupCheckable(0, false, true)
        bottomNavigationView.selectedItemId = R.id.menu_cart

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.menu_cart -> {
                    startActivity(Intent(this, CarritoActivity::class.java))
                    true
                }
                R.id.menu_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupProductButtons() {
        val products = listOf(
            Triple("Cuernito", 10.0, R.drawable.cuernito),
            Triple("Magdalenas", 8.0, R.drawable.magdalenas),
            Triple("Panque de Vainilla", 30.0, R.drawable.panquevainilla),
            Triple("Roles de Canela", 15.0, R.drawable.roles),
            Triple("Trenza", 20.0, R.drawable.trenza),
            Triple("Cubilete", 12.0, R.drawable.cubilete),
            Triple("Donas", 10.0, R.drawable.donas),
            Triple("Concha", 10.0, R.drawable.conchavainilla),
            Triple("Chilindrina", 10.0, R.drawable.chilindrina),
            Triple("Rebanada", 10.0, R.drawable.rebanada)
        )

        products.forEachIndexed { index, (name, price, imageResId) ->
            val addButton = findViewById<Button>(resources.getIdentifier("btnAgregarAlCarrito${index + 1}", "id", packageName))
            val removeButton = findViewById<Button>(resources.getIdentifier("btnEliminar${index + 1}", "id", packageName))
            val quantityText = findViewById<TextView>(resources.getIdentifier("contProductos${index + 1}", "id", packageName))

            addButton.setOnClickListener {
                viewModel.addToCart(CartItem(name, price, imageResId))
                updateQuantity(quantityText, name)
            }

            removeButton.setOnClickListener {
                viewModel.removeFromCart(name)
                updateQuantity(quantityText, name)
            }

            updateQuantity(quantityText, name)
        }
    }

    private fun updateQuantity(textView: TextView, productName: String) {
        val quantity = viewModel.getQuantity(productName)
        textView.text = "Cantidad: $quantity"
    }

    override fun onResume() {
        super.onResume()
        setupProductButtons()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}