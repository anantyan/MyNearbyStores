package com.example.mynearbystore.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import com.example.mynearbystore.R
import com.example.mynearbystore.databinding.ActivityProfileBinding
import com.example.mynearbystore.ui.main.MainActivity
import com.example.mynearbystore.ui.store.StoreActivity
import com.example.mynearbystore.util.Constant.PERMISSION_COARSE_AND_FINE_LOCATION
import com.example.mynearbystore.util.requestLocation
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.EasyPermissions.somePermissionPermanentlyDenied
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        binding.imgView.load(R.drawable.profile_github) {
            transformations(RoundedCornersTransformation(200F))
            size(ViewSizeResolver(binding.imgView))
        }

        binding.btnStores.setOnClickListener {
            /**
             * IMPLEMENTASI EASY PERMISSION ON CLICK
             * */
            requestLocation(this)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
    }


    /**
     * PERMISSION DENGAN DIBANTU
     * LIBRARY PIHAK KE-3
     * EASY PERMISSION
     * =============================================================================================
     * */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (somePermissionPermanentlyDenied(this, listOf(perms[0])) &&
            somePermissionPermanentlyDenied(this, listOf(perms[1]))
        ) {
            SettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == PERMISSION_COARSE_AND_FINE_LOCATION) {
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    /**
     * =============================================================================================
     * */
}