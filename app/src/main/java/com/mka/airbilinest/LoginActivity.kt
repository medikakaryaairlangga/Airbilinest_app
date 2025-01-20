package com.mka.airbilinest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mka.airbilinest.database.DatabaseHelper
import com.mka.airbilinest.model.user
//import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private val databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnRegisterListener()
        btnLoginListener()
    }

    private fun btnRegisterListener() {

        val BtnRegister = findViewById<Button>(R.id.btnRegister)
        val TxtUsername = findViewById<EditText>(R.id.txtUsername)
        val TxtPassword = findViewById<EditText>(R.id.txtPassword)
        BtnRegister.setOnClickListener {
            if (!databaseHelper.checkUser(TxtUsername.text.toString().trim())) {
                val user = user().apply {
                    username = TxtUsername.text.toString().trim()
                    password = TxtPassword.text.toString().trim()
                }
                databaseHelper.addUser(user)
                Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btnLoginListener() {
        val BtnLogin = findViewById<Button>(R.id.btnLogin)
        val TxtUsername = findViewById<EditText>(R.id.txtUsername)
        val TxtPassword = findViewById<EditText>(R.id.txtPassword)
        BtnLogin.setOnClickListener {
            if (databaseHelper.checkUser(TxtUsername.text.toString().trim(), TxtPassword.text.toString().trim())) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Username or password is wrong, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

