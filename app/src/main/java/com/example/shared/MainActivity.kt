package com.example.shared

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var editTextRut: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        editTextRut = findViewById(R.id.editTextRut)
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        textViewResult = findViewById(R.id.textViewResult)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val rut = editTextRut.text.toString()
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()

            editor.putString(rut, "$name#$email")
            editor.apply()

            editTextRut.text.clear()
            editTextName.text.clear()
            editTextEmail.text.clear()
        }

        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            val rutToSearch = editTextRut.text.toString()
            val userData = sharedPreferences.getString(rutToSearch, null)

            if (userData != null) {
                val parts = userData.split("#")
                val name = parts[0]
                val email = parts[1]
                textViewResult.text = "Nombre: $name\nCorreo: $email"
            } else {
                showAlertDialog()
                textViewResult.text = ""
            }
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Usuario no encontrado")
            .setMessage("El usuario con el Rut especificado no existe.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
