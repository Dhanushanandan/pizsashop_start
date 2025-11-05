package com.example.pizzashop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzashop.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val pass = binding.password.text.toString().trim()
            if (email.isEmpty() || pass.isEmpty()) { Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    // store simple user profile in Realtime DB
                    val uid = auth.currentUser!!.uid
                    val db = FirebaseDatabase.getInstance().getReference("customers").child(uid)
                    val user = mapOf("name" to name, "email" to email)
                    db.setValue(user)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Signup failed: ${it.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
