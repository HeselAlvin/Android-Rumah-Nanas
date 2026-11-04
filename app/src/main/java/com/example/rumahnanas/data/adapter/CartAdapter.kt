package com.example.rumahnanas.data.adapter

import android.support.v4.os.IResultReceiver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rumahnanas.R
import com.example.rumahnanas.data.model.Cart

class CartAdapter (
    private val cartList: MutableList<Cart>,
    private val onCartUpdate: () -> Unit
): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cbItem: CheckBox = itemView.findViewById(R.id.cbCartItem)
        val ivImage: ImageView = itemView.findViewById(R.id.ivCartItemImage)
        val tvName: TextView = itemView.findViewById(R.id.tvCartItemName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvCartItemPrice)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvCartQuantity)
        val btnMinus: ImageView = itemView.findViewById(R.id.btnCartMinus)
        val btnPlus: ImageView = itemView.findViewById(R.id.btnCartPlus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        holder.tvName.text = item.name
        holder.tvPrice.text = "Rp ${item.Price}"
        holder.tvQuantity.text = item.quantity.toString()

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.ivImage)

        holder.cbItem.setOnCheckedChangeListener (null)
        holder.cbItem.isChecked = item.isSelected

        holder.cbItem.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            onCartUpdate()
        }

        holder.btnPlus.setOnClickListener {
            item.quantity++
            holder.tvQuantity.text = item.quantity.toString()
            holder.cbItem.isChecked = true
            onCartUpdate()
            com.example.rumahnanas.utils.CartManager.saveCart(holder.itemView.context)
        }

        holder.btnMinus.setOnClickListener {
            if (item.quantity > 1){
                item.quantity--
                holder.tvQuantity.text = item.quantity.toString()
                holder.cbItem.isChecked = true
                onCartUpdate()
                com.example.rumahnanas.utils.CartManager.saveCart(holder.itemView.context)
            } else {
                Toast.makeText(holder.itemView.context,"Minimal 1 item, hapus dari keranjang?", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}