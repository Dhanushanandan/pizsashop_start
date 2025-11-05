package com.example.pizzashop

import com.example.pizzashop.models.Pizza

object Cart {
    val items = mutableListOf<Pizza>()
    fun add(p: Pizza) { items.add(p) }
    fun clear() = items.clear()
}
