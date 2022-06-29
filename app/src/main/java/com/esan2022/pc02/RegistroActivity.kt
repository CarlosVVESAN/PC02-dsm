package com.esan2022.pc02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.esan2022.pc02.models.UserModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.time.Instant
import java.util.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val textViewDNI: EditText = findViewById(R.id.textViewDNI)
        val editTextNameFull: EditText = findViewById(R.id.editTextNameFull)
        val textViewPass: EditText = findViewById(R.id.textViewPass)
        val textViewPassRepeat: EditText = findViewById(R.id.textViewPassRepeat)

        val buttonRRegister: Button = findViewById(R.id.buttonRRegister)
        buttonRRegister.setOnClickListener {
            if (textViewPass.text.toString().equals(textViewPassRepeat.text.toString())) {
                val userM = UserModel(
                    textViewDNI.text.toString(),
                    editTextNameFull.text.toString(),
                    textViewPass.text.toString()
                )
                val db = Firebase.firestore
                db.collection("usersCollection")
                    .whereEqualTo("dni", userM.dni)
                    .get()
                    .addOnSuccessListener { documents ->
                        var countDocs = 0
                        for (document in documents) {
                            countDocs++
                        }
                        if (countDocs == 0) {
                            db.collection("usersCollection").add(userM)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registrado correctamente.", Toast.LENGTH_LONG).show()
                                    val intent: Intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Ocurrió un error.", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            Toast.makeText(this, "Ya existe un usuario con el DNI: ${userM.dni}", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
                        Toast.makeText(this, "Error getting documents", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Las claves deben de ser iguales...", Toast.LENGTH_LONG).show()
            }
        }
    }
}