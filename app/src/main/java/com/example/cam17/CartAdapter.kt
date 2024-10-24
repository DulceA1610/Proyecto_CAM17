package com.example.cam17

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private var items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.productImage)
        val nameTextView: TextView = view.findViewById(R.id.productName)
        val priceTextView: TextView = view.findViewById(R.id.productPrice)
        val quantityTextView: TextView = view.findViewById(R.id.productQuantity)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "$${String.format("%.2f", item.price)}"
        holder.quantityTextView.text = "Cantidad: ${item.quantity}"

        // Configurar el CheckBox
        holder.checkBox.isChecked = item.isSelected
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            onItemSelected(position, isChecked)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun onItemSelected(position: Int, isSelected: Boolean) {
    }
}
