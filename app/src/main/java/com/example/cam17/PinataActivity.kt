package com.example.cam17

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class PinataActivity : BaseActivity() {
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContent(R.layout.activity_pinata)

        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        setupBottomNavigation()
        setupProductButtons()
        observeCartItems()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.menu.setGroupCheckable(0, false, true)
        bottomNavigationView.selectedItemId = R.id.menu_cart

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.menu_cart -> {
                    startActivity(Intent(this, CarritoActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.menu_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupProductButtons() {
        val products = listOf(
            Triple("Estrella de 5 picos", 80.0, R.drawable.estrella),
            Triple("Estrella de 6 picos", 100.0, R.drawable.estrella6),
            Triple("Piñata de Bob Esponja", 200.0, R.drawable.bob),
            Triple("Piñata de Cactus", 200.0, R.drawable.cactus),
            Triple("Piñata de Dinosaurio", 200.0, R.drawable.dino),
            Triple("Piñata de Mini", 200.0, R.drawable.mini),
            Triple("Piñata de Paw Patrol", 200.0, R.drawable.pawpatrol),
            Triple("Piñata redonda de Mario", 150.0, R.drawable.mario),
            Triple("Piñata de numero 5", 200.0, R.drawable.numero5),
            Triple("Piñata de numero 6", 200.0, R.drawable.numero6)
        )

        products.forEachIndexed { index, (name, price, imageResId) ->
            val addButton = findViewById<Button>(resources.getIdentifier("btnAgregarAlCarrito${index + 21}", "id", packageName))
            val removeButton = findViewById<Button>(resources.getIdentifier("btnEliminar${index + 21}", "id", packageName))
            val quantityText = findViewById<TextView>(resources.getIdentifier("contProductos${index + 21}", "id", packageName))

            addButton.setOnClickListener {
                viewModel.addToCart(CartItem(name, price, imageResId))
            }

            removeButton.setOnClickListener {
                viewModel.removeFromCart(name)
            }

            quantityText.text = "Cantidad: ${viewModel.getQuantity(name)}"
        }
    }

    private fun observeCartItems() {
        viewModel.cartItems.observe(this) { items ->
            updateAllQuantities(items)
        }
    }

    private fun updateAllQuantities(items: List<CartItem>) {
        val products = listOf(
            "Estrella de 5 picos", "Estrella de 6 picos", "Piñata de Bob Esponja",
            "Piñata de Cactus", "Piñata de Dinosaurio", "Piñata de Mini",
            "Piñata de Paw Patrol", "Piñata redonda de Mario", "Piñata de numero 5",
            "Piñata de numero 6"
        )

        products.forEachIndexed { index, name ->
            val quantityText = findViewById<TextView>(resources.getIdentifier("contProductos${index + 21}", "id", packageName))
            val quantity = items.find { it.name == name }?.quantity ?: 0
            quantityText.text = "Cantidad: $quantity"
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.cartItems.value?.let { updateAllQuantities(it) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}