package com.example.rumahnanas.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rumahnanas.R
import com.example.rumahnanas.data.model.Address

class AddressAdapter(
    private var addressList: List<Address>,
    private val onAddressClick: (Address) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = itemView.findViewById(R.id.tvItemlabel)
        val tvNamePhone: TextView = itemView.findViewById(R.id.tvItemNamePhone)
        val tvCompleteAddress: TextView = itemView.findViewById(R.id.tvItemAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addressList[position]
        holder.tvLabel.text = address.label
        holder.tvNamePhone.text = "${address.receiverName} | ${address.phoneNumber}"
        holder.tvCompleteAddress.text = address.completeAddress

        holder.itemView.setOnClickListener {
            onAddressClick(address)
        }
    }

    override fun getItemCount() = addressList.size

    fun updateData(newList: List<Address>) {
        addressList = newList
        notifyDataSetChanged()
    }
}