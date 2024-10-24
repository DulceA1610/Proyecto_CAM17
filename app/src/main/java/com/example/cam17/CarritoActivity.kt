package com.example.cam17

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CarritoActivity : BaseActivity() {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContent(R.layout.activity_carrito)

        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        setupBottomNavigation()
        setupCartRecyclerView()
        setupTotalAndPayButton()
        val btnPagar: Button=findViewById(R.id.btnpagar)
        btnPagar.setOnClickListener {
            val intent= Intent(this, Pagar::class.java)
            startActivity(intent)
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.menu_cart

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.menu_cart -> true
                R.id.menu_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupCartRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.pagarreclic)
        val emptyCartTextView = findViewById<TextView>(R.id.emptyCartTextView)
        cartAdapter = CartAdapter(viewModel.getCartItems())
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.cartItems.observe(this) { items ->
            cartAdapter.updateItems(items)
            updateTotal()

            if (items.isEmpty()) {
                recyclerView.visibility = RecyclerView.GONE
                emptyCartTextView.visibility = TextView.VISIBLE
            } else {
                recyclerView.visibility = RecyclerView.VISIBLE
                emptyCartTextView.visibility = TextView.GONE
            }
        }
    }

    private fun setupTotalAndPayButton() {
        val totalTextView = findViewById<TextView>(R.id.totalTextView)
        val payButton = findViewById<Button>(R.id.btnpagar)

        updateTotal()

        payButton.setOnClickListener {
            val intent = Intent(this, Pagar::class.java)
            intent.putParcelableArrayListExtra("cartItems", ArrayList(viewModel.getCartItems()))
            intent.putExtra("total", viewModel.getTotal())
            startActivity(intent)
        }
    }

    private fun updateTotal() {
        val totalTextView = findViewById<TextView>(R.id.totalTextView)
        val total = viewModel.getTotal()
        totalTextView.text = "Total: $${String.format("%.2f", total)}"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
    private fun borrarselect(){
        val btnBorrar: Button=findViewById(R.id.btn_borrar)
    }

}