package com.example.pizzashop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizzashop.databinding.ItemPizzaBinding
import com.example.pizzashop.models.Pizza

class PizzasAdapter(
    private var list: List<Pizza>,
    private val onAdd: (Pizza) -> Unit
) : RecyclerView.Adapter<PizzasAdapter.VH>() {

    inner class VH(val binding: ItemPizzaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemPizzaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val pizza = list[position]

        holder.binding.pizzaName.text = pizza.name
        holder.binding.pizzaDesc.text = pizza.description
        holder.binding.pizzaPrice.text = "$${pizza.price}"

        Glide.with(holder.itemView.context)
            .load(pizza.imageUrl)
            .placeholder(com.example.pizzashop.R.drawable.pizza_logo)
            .into(holder.binding.pizzaImage)

        holder.binding.addToCartButton.setOnClickListener { onAdd(pizza) }
    }

    override fun getItemCount(): Int = list.size

    fun update(newList: List<Pizza>) {
        list = newList
        notifyDataSetChanged()
    }
}
