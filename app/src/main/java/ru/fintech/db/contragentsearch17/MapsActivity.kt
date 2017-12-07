package ru.fintech.db.contragentsearch17

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val zoomLevel : Float = 16.0f
    lateinit var name: String
    lateinit var place: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val input = intent.getStringExtra("Location")
        if (BuildConfig.DEBUG) {
            Log.w("14", input)
        }
        try {
            val (lat, lon) = input.split('_').map { it.toDouble() }
            place = LatLng(lat, lon)
        }
        catch (e: Exception) {
            Toast.makeText(applicationContext, "Place invalid", Toast.LENGTH_SHORT).show()
            finish()
        }
        name=intent.getStringExtra("Name")
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(place).title(name))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,zoomLevel))
    }
}
