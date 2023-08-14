package com.example.mynearbystore.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import com.example.mynearbystore.R
import com.example.mynearbystore.databinding.ActivityDashboardBinding
import com.example.mynearbystore.util.Constant
import com.example.mynearbystore.util.Constant.PASSING_FROM_STORE_DETAIL_TO_STORE_DASHBOARD
import com.example.mynearbystore.util.Constant.PASSING_FROM_STORE_TO_STORE_DETAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel by viewModels<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.getItem.observe(this) {
            binding.txtStoreCode.text = it.storeCode
            binding.txtStoreName.text = it.storeName
            binding.txtClusterArea.text = it.areaName
            binding.txtAddress.text = it.address
        }
        viewModel.checkItem.observe(this) {
            val toast = Toast.makeText(this, it, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    private fun bindView() {
        val id = intent.getIntExtra(PASSING_FROM_STORE_DETAIL_TO_STORE_DASHBOARD, 0)
        viewModel.getItem(id)

        binding.imageView2.load(R.drawable.profile_github) {
            transformations(RoundedCornersTransformation(200F))
            size(ViewSizeResolver(binding.imageView2))
        }

        binding.btnDone.setOnClickListener {
            viewModel.checkItem(id)
            onBackPressedDispatcher.onBackPressed()
        }
    }
}