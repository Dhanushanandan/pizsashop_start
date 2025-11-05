package com.example.pizzashop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzashop.adapters.PizzasAdapter
import com.example.pizzashop.databinding.ActivityMainBinding
import com.example.pizzashop.models.Pizza
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pizzas = mutableListOf<Pizza>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pizzaRecycler.layoutManager = LinearLayoutManager(this)
        val adapter = PizzasAdapter(pizzas){ p ->
            Cart.add(p)
            Toast.makeText(this, "Added to cart: ${p.name}", Toast.LENGTH_SHORT).show()
        }
        binding.pizzaRecycler.adapter = adapter

        binding.openCartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // Load pizzas from Realtime Database node "pizzas"
        val ref = FirebaseDatabase.getInstance().getReference("pizzas")
        ref.get().addOnSuccessListener { snap ->
            val list = mutableListOf<Pizza>()
            for (c in snap.children) {
                val p = c.getValue(Pizza::class.java)
                if (p != null) list.add(p)
            }
            pizzas.clear(); pizzas.addAll(list); adapter.update(list)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load pizzas", Toast.LENGTH_SHORT).show()
        }
    }
}
