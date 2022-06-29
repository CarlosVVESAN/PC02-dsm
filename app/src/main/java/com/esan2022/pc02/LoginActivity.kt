package com.esan2022.pc02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.esan2022.pc02.models.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textViewLDNI: EditText = findViewById(R.id.textViewLDNI)
        val textViewLPass: EditText = findViewById(R.id.textViewLPass)

        val btnRegister: Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        val btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val db = Firebase.firestore
            db.collection("usersCollection")
                .whereEqualTo("dni", textViewLDNI.text.toString())
                .whereEqualTo("password", textViewLPass.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    var countDocs = 0
                    for (document in documents) {
                        countDocs++
                    }
                    if (countDocs == 0) {
                        Toast.makeText(this, "Usuario o contraseña incorrecta.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "ACCESO PERMITIDO", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting documents", Toast.LENGTH_LONG).show()
                }
        }
    }
}