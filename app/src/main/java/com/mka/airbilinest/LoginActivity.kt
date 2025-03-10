package com.mka.airbilinest

import ApiResponse
import ApiService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : BaseActivity() {
    private lateinit var apiService: ApiService
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://airbilinest.com/API/") // Ganti dengan URL server Anda
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Cek apakah ada data login yang tersimpan
        checkSavedLogin()

        btnLoginListener()

        val web_btn = findViewById<Button>(R.id.WebBTN)
        web_btn.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://airbilinest.com/hcp/register.php"))
            startActivity(i)
        }
    }

    private fun checkSavedLogin() {
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("password", null)

        if (savedEmail != null && savedPassword != null) {
            // Auto-fill email dan password
            findViewById<EditText>(R.id.txtEmail).setText(savedEmail)
            findViewById<EditText>(R.id.txtPassword).setText(savedPassword)
            findViewById<CheckBox>(R.id.checkRememberMe).isChecked = true
        }
    }

    private fun btnLoginListener() {
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val checkRememberMe = findViewById<CheckBox>(R.id.checkRememberMe)

        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if (checkRememberMe.isChecked) {
                // Simpan email dan password jika "Ingat Saya" dicentang
                saveLoginDetails(email, password)
            } else {
                // Hapus data yang tersimpan jika "Ingat Saya" tidak dicentang
                clearLoginDetails()
            }

            // Proses login
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        apiService.login(email, password).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Login successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveLoginDetails(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun clearLoginDetails() {
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("password")
        editor.apply()
    }
}