package com.example.mynearbystore.ui.store_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mynearbystore.R
import com.example.mynearbystore.databinding.ActivityStoreDetailBinding
import com.example.mynearbystore.ui.dashboard.DashboardActivity
import com.example.mynearbystore.util.Constant.PASSING_FROM_STORE_DETAIL_TO_STORE_DASHBOARD
import com.example.mynearbystore.util.Constant.PASSING_FROM_STORE_TO_STORE_DETAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreDetailBinding
    private val viewModel by viewModels<StoreDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObsever()
        bindView()
    }

    private fun bindView() {
        val id = intent.getIntExtra(PASSING_FROM_STORE_TO_STORE_DETAIL, 0)
        viewModel.getItem(id)

        binding.btnBack.setOnClickListener {
            viewModel.checkItem(id)
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@StoreDetailActivity, DashboardActivity::class.java)
            intent.putExtra(PASSING_FROM_STORE_DETAIL_TO_STORE_DASHBOARD, id)
            startActivity(intent)
        }
    }

    private fun bindObsever() {
        viewModel.getItem.observe(this) {
            binding.txtNamaToko.text = it.storeName
            binding.txtAddress.text = it.address
            binding.txtTipeOutlet.text = it.areaName
        }
        viewModel.checkItem.observe(this) {
            val toast = Toast.makeText(this, it, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }
}