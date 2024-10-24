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

class PinturaActivity : BaseActivity() {
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContent(R.layout.activity_pintura)

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
            Triple("Alcancia Unicornio", 80.0, R.drawable.alcanciaunicornio),
            Triple("Alcancia Cerdito Corazones", 80.0, R.drawable.cerditocorazones),
            Triple("Alcancia Cerdito Elefante", 80.0, R.drawable.cerditoelefante),
            Triple("Alcancia Cerdito Vaquita", 80.0, R.drawable.cerditovaquita),
            Triple("Alcancia Forky", 80.0, R.drawable.forky),
            Triple("Maceta Cactus", 60.0, R.drawable.macetacactus),
            Triple("Maceta Tortuga", 60.0, R.drawable.macetatortuga),
            Triple("Taza Cactus", 70.0, R.drawable.tazacactus),
            Triple("Taza Unicornio", 70.0, R.drawable.tazaunicornio),
            Triple("Cuadro Mariposa", 80.0, R.drawable.cuadromariposa)
        )

        products.forEachIndexed { index, (name, price, imageResId) ->
            val addButton = findViewById<Button>(resources.getIdentifier("btnAgregarAlCarrito${index + 11}", "id", packageName))
            val removeButton = findViewById<Button>(resources.getIdentifier("btnEliminar${index + 11}", "id", packageName))
            val quantityText = findViewById<TextView>(resources.getIdentifier("contProductos${index + 11}", "id", packageName))

            addButton.setOnClickListener {
                viewModel.addToCart(CartItem(name, price, imageResId))
            }

            removeButton.setOnClickListener {
                viewModel.removeFromCart(name)
            }

            updateQuantityText(name, quantityText)
        }
    }

    private fun observeCartItems() {
        viewModel.cartItems.observe(this) { items ->
            updateAllQuantities(items)
        }
    }

    private fun updateAllQuantities(items: List<CartItem>) {
        val products = listOf(
            "Alcancia Unicornio", "Alcancia Cerdito Corazones", "Alcancia Cerdito Elefante", "Alcancia Cerdito Vaquita",
            "Alcancia Forky", "Maceta Cactus", "Maceta Tortuga", "Taza Cactus",
            "Taza Unicornio", "Cuadro Mariposa"
        )

        products.forEachIndexed { index, name ->
            val quantityText = findViewById<TextView>(resources.getIdentifier("contProductos${index + 11}", "id", packageName))
            updateQuantityText(name, quantityText)
        }
    }

    private fun updateQuantityText(productName: String, textView: TextView) {
        val quantity = viewModel.getQuantity(productName)
        textView.text = "Cantidad: $quantity"
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