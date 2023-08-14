package com.example.mynearbystore.ui.store

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynearbystore.R
import com.example.mynearbystore.databinding.ActivityStoreBinding
import com.example.mynearbystore.ui.store_detail.StoreDetailActivity
import com.example.mynearbystore.util.Constant.PASSING_FROM_STORE_TO_STORE_DETAIL
import com.example.mynearbystore.util.Constant.PERMISSION_COARSE_AND_FINE_LOCATION
import com.example.mynearbystore.util.Resource
import com.example.mynearbystore.util.onDecorationListener
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var mapView: MapView
    private var userMarker: Marker? = null
    private val viewModel by viewModels<StoreViewModel>()
    private val adapter: StoreAdapter by lazy { StoreAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        mapView.overlays.add(0, mapEventOverlay())

        binding.rvStores.layoutManager = LinearLayoutManager(this)
        binding.rvStores.itemAnimator = DefaultItemAnimator()
        binding.rvStores.setHasFixedSize(true)
        binding.rvStores.addItemDecoration(this.onDecorationListener(LinearLayout.VERTICAL, 16))
        binding.rvStores.adapter = adapter

        binding.btnCurrentLocation.setOnClickListener {
            viewModel.getCurrentLocation()
        }

        binding.txtSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchLocation(query)
                }
                true
            } else {
                false
            }
        }

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.setOnItemClickListener { storesItemLocal, _ ->
            val intent = Intent(this@StoreActivity, StoreDetailActivity::class.java)
            intent.putExtra(PASSING_FROM_STORE_TO_STORE_DETAIL, storesItemLocal.id)
            startActivity(intent)
        }
    }

    /**
     * CONFIGURE OSM & PLAY SERVICES LOCATION
     * =============================================================================================
     * */
    private fun mapEventOverlay() = MapEventsOverlay(object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
            // Tambahkan marker yang dapat digeser ke lokasi yang ditekan
            return false
        }

        override fun longPressHelper(p: GeoPoint?): Boolean {
            // Opsional: Anda bisa menangani long press di sini jika diperlukan
            p?.let {
                setLocationMarker(it.latitude, it.longitude)
                viewModel.nearbyStores(it)
            }
            return true
        }
    })
    private fun setLocationMarker(latitude: Double, longitude: Double) {
        userMarker?.let {
            mapView.overlays.remove(it)
        }

        val startPoint = GeoPoint(latitude, longitude)
        val marker = Marker(mapView)
        marker.position = startPoint
        marker.isDraggable = true
        marker.icon = ResourcesCompat.getDrawable(resources, R.drawable.baseline_location_on_24, null)
        marker.setOnMarkerDragListener(object : Marker.OnMarkerDragListener {
            override fun onMarkerDrag(marker: Marker?) {
                // Aksi saat marker digeser
            }

            override fun onMarkerDragEnd(marker: Marker?) {
                marker?.position?.let {
                    viewModel.nearbyStores(it)
                }
            }

            override fun onMarkerDragStart(marker: Marker?) {
                // Aksi saat marker mulai digeser
            }

        })

        mapView.overlays.add(marker)
        userMarker = marker
        mapView.invalidate()
    }
    /**
     * =============================================================================================
     * */

    /**
     * CONFIGURE OBSERVER VIEWMODEL
     * =============================================================================================
     * */
    private fun bindObserver() {
        viewModel.isEmpty.observe(this) {
            if (it) {
                binding.txtEmpty.visibility = View.VISIBLE
                binding.rvStores.visibility = View.GONE
            } else {
                binding.txtEmpty.visibility = View.GONE
                binding.rvStores.visibility = View.VISIBLE
            }
        }
        viewModel.currentLocation.observe(this) {
            mapView.controller.setCenter(it)
            mapView.controller.setZoom(15.0)
            if (userMarker == null) {
                setLocationMarker(it.latitude, it.longitude)
            } else {
                userMarker?.position = it
            }
            mapView.invalidate()
            viewModel.nearbyStores(it)
        }
        viewModel.nearbyStores.observe(this) {
            adapter.submitList(it)
        }
        viewModel.searchLocation.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    val toast = Toast.makeText(this, "Loading...", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                is Resource.Success -> {
                    it.data?.let { geo ->
                        mapView.controller.setCenter(geo)
                        mapView.controller.setZoom(15.0)
                        if (userMarker == null) {
                            setLocationMarker(geo.latitude, geo.longitude)
                        } else {
                            userMarker?.position = geo
                        }
                        mapView.invalidate()
                        viewModel.nearbyStores(geo)
                    }
                }
                is Resource.Error -> {
                    val toast = Toast.makeText(this, it.error?.message.toString(), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
        }
        viewModel.getCurrentLocation()
        viewModel.getStores()
    }
    /**
     * =============================================================================================
     * */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_COARSE_AND_FINE_LOCATION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.getCurrentLocation()
        }
    }
}