package au.commbank.codingchallenge.screens.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.BaseActivity
import au.commbank.codingchallenge.screens.account.data.Location
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions


class AtmLocationActivity : BaseActivity(), OnMapReadyCallback {

    companion object {
        private const val LOCATION = "Location"
        fun getLaunchIntent(context: Context, location: Location): Intent =
            Intent(context, AtmLocationActivity::class.java).apply {
                putExtra(LOCATION, location)
            }

        fun getLocationFromIntent(intent: Intent): Location? = intent.getParcelableExtra(LOCATION)
    }

    lateinit var location: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)

        val passedLocation: Location? = getLocationFromIntent(intent)
        if (passedLocation == null) {
            Toast.makeText(
                this,
                getString(R.string.info_pass_valid_location),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            location = passedLocation
            initViews()
        }
    }

    private fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initActionBar(
            toolBar = toolbar,
            titleText = getString(R.string.find_us),
            homeAsUpEnabled = true
        )
        (supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment)
            .getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.apply {
            val latLng = LatLng(location.latitude, location.longitude)
            addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            )
            moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }
}