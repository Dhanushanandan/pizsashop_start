package com.example.pizzashop

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzashop.adapters.PizzasAdapter
import com.example.pizzashop.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        val adapter = PizzasAdapter(Cart.items){ }
        binding.cartRecycler.adapter = adapter

        binding.placeOrderBtn.setOnClickListener {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: run { Toast.makeText(this, "Login to place order", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            val db = FirebaseDatabase.getInstance().getReference("orders").child(uid)
            val order = Cart.items.map { it.id }
            db.push().setValue(order).addOnSuccessListener {
                Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show()
                Cart.clear()
                adapter.update(Cart.items)
            }.addOnFailureListener {
                Toast.makeText(this, "Order failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
