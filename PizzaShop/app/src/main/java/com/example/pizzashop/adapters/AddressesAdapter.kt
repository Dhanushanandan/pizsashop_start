package com.example.pizzashop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzashop.databinding.ItemDeliveryAddressBinding
import com.example.pizzashop.models.CustomerAddress

class AddressesAdapter(private var list: List<CustomerAddress>, private val onDelete: (CustomerAddress)->Unit) : RecyclerView.Adapter<AddressesAdapter.VH>() {
    inner class VH(val binding: ItemDeliveryAddressBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemDeliveryAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val a = list[position]
        holder.binding.addressText.text = "${a.street}, ${a.city}, ${a.postalCode}"
        holder.binding.deleteButton.setOnClickListener { onDelete(a) }
    }
    override fun getItemCount(): Int = list.size
    fun update(newList: List<CustomerAddress>){ list = newList; notifyDataSetChanged() }
}
