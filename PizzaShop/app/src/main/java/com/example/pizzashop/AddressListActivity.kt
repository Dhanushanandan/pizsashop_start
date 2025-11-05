package com.example.pizzashop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzashop.adapters.AddressesAdapter
import com.example.pizzashop.databinding.ActivityAddressListBinding
import com.example.pizzashop.models.CustomerAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddressListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressListBinding
    private val addresses = mutableListOf<CustomerAddress>()
    private lateinit var adapter: AddressesAdapter
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseDatabase.getInstance().getReference("customers") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AddressesAdapter(addresses){ addr ->
            // delete
            val uid = auth.currentUser?.uid ?: return@AddressesAdapter
            db.child(uid).child("addresses").get().addOnSuccessListener { snap ->
                val list = mutableListOf<CustomerAddress>()
                for (c in snap.children) {
                    val a = c.getValue(CustomerAddress::class.java)
                    if (a != null) list.add(a)
                }
                // remove first matching
                val toRemove = list.indexOfFirst { it.street==addr.street && it.city==addr.city && it.postalCode==addr.postalCode }
                if (toRemove>=0) {
                    snap.children.elementAt(toRemove).ref.removeValue()
                    Toast.makeText(this, "Address removed", Toast.LENGTH_SHORT).show()
                    loadAddresses()
                }
            }
        }

        binding.addressesRecycler.layoutManager = LinearLayoutManager(this)
        binding.addressesRecycler.adapter = adapter

        binding.addAddressBtn.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }

        loadAddresses()
    }

    private fun loadAddresses(){
        val uid = auth.currentUser?.uid ?: return
        db.child(uid).child("addresses").get().addOnSuccessListener { snap ->
            val list = mutableListOf<CustomerAddress>()
            for (c in snap.children) {
                val a = c.getValue(CustomerAddress::class.java)
                if (a!=null) list.add(a)
            }
            addresses.clear(); addresses.addAll(list); adapter.update(list)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load addresses", Toast.LENGTH_SHORT).show()
        }
    }
}
