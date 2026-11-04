package com.example.rumahnanas.data.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rumahnanas.R
import com.example.rumahnanas.data.model.Product
import com.example.rumahnanas.databinding.ItemProductBinding
import com.example.rumahnanas.ui.DetailProductActivity

class ProductAdapter (private val productList: List<Product>):
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    inner class ProductViewHolder(val binding: ItemProductBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.tvProductName.text = product.name
        holder.binding.tvProductprice.text = "Rp ${product.price}"

        Glide.with(holder.itemView.context)
            .load("http://10.0.2.2:8000/storage/"+product.image)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.binding.ivProductImage)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = android.content.Intent(context, DetailProductActivity::class.java)

            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_PRICE", product.price.toString())
            intent.putExtra("PRODUCT_IMAGE", product.image)

            intent.putExtra("PRODUCT_DESC", product.description)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  productList.size
    }

}