package com.example.mynearbystore.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mynearbystore.databinding.ActivityMainBinding
import com.example.mynearbystore.ui.profile.ProfileActivity
import com.example.mynearbystore.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                username = binding.txtUsername.text.toString(),
                password = binding.txtPassword.text.toString(),
            )
        }
    }

    private fun bindObserver() {
        if (viewModel.logged) {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.login.observe(this) {
            when(it) {
                is Resource.Loading -> {
                    val toast = Toast.makeText(this, "Loading...", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                is Resource.Success -> {
                    val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    val toast = Toast.makeText(this, it.error?.message.toString(), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
        }
    }
}