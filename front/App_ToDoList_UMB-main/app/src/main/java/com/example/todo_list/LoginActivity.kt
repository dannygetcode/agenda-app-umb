package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_list.databinding.ActivityLoginBinding
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()


            if (username.isNotEmpty() && password.isNotEmpty()) {

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }


    }


}
