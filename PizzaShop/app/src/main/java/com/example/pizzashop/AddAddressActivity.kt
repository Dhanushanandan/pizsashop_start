package com.example.pizzashop

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzashop.databinding.ActivityAddAddressBinding
import com.example.pizzashop.models.CustomerAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseDatabase.getInstance().getReference("customers") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveAddressBtn.setOnClickListener {
            val street = binding.street.text.toString().trim()
            val city = binding.city.text.toString().trim()
            val postal = binding.postal.text.toString().trim()
            if (street.isEmpty() || city.isEmpty()) { Toast.makeText(this, "Fill fields", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            val addr = CustomerAddress(street, city, postal)
            val uid = auth.currentUser?.uid ?: return@setOnClickListener
            db.child(uid).child("addresses").push().setValue(addr).addOnSuccessListener {
                Toast.makeText(this, "Address saved", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
