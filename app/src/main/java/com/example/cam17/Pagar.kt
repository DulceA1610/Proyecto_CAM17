package com.example.cam17

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Pagar : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagar)

        recyclerView = findViewById(R.id.pagarreclic)
        totalTextView = findViewById(R.id.totalTextView)

        val cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems") ?: arrayListOf()
        val total = intent.getDoubleExtra("total", 0.0)

        setupRecyclerView(cartItems)
        setupTotalText(total)
    }

    private fun setupRecyclerView(cartItems: List<CartItem>) {
        adapter = CartAdapter(cartItems)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun setupTotalText(total: Double) {
        totalTextView.text = "Total a pagar: $${String.format("%.2f", total)}"
    }
}