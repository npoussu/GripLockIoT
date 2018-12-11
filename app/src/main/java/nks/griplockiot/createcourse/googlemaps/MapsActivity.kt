package nks.griplockiot.createcourse.googlemaps

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import nks.griplockiot.R
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import java.math.RoundingMode
import java.text.DecimalFormat

@ObsoleteCoroutinesApi
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, CoroutineScope {

    // Reference: The following code is heavily inspired by the tutorial below
    // https://www.raywenderlich.com/230-introduction-to-google-maps-api-for-android-with-kotlin

    private lateinit var mLocation: LatLng

    override fun onMapClick(position: LatLng?) {
        mMap.clear()
        val latLng = position as LatLng
        mLocation = latLng
        val markerOptions = MarkerOptions().position(latLng)
        val df = DecimalFormat("#.######")
        df.roundingMode = RoundingMode.CEILING
        markerOptions.title("Latitude: " + df.format(latLng.latitude) + " Longitude: " + df.format(latLng.longitude))
        mMap.addMarker(markerOptions)
    }

    @ObsoleteCoroutinesApi
    override
    val coroutineContext = newFixedThreadPoolContext(2, "bg")

    override fun onMarkerClick(p0: Marker?) = false

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var course: Course

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val MAP_TYPE_SATELLITE = GoogleMap.MAP_TYPE_SATELLITE
        private const val MAP_TYPE_NORMAL = GoogleMap.MAP_TYPE_NORMAL
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true

        if (course.latitude != null && course.longitude != null) {
            val currentLatLng = LatLng(course.latitude as Double, course.longitude as Double)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            val markerOptions = MarkerOptions().position(currentLatLng)
            val df = DecimalFormat("#.######")
            df.roundingMode = RoundingMode.CEILING
            markerOptions.title("Latitude: " + df.format(currentLatLng.latitude) + " Longitude: " + df.format(currentLatLng.longitude))
            mMap.addMarker(markerOptions)
        }

        mMap.mapType = MAP_TYPE_SATELLITE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        runBlocking(coroutineContext) {
            course = intent.extras["course"] as Course
            course = AppDatabase.getInstance(applicationContext).getCourseDAO().getCourse(course.id as Int)
        }

        title = "Course: " + "'" + course.name + "'" + " on map"

        // If the user hasn't selected a location before, prompt user to do so
        if (course.latitude == null || course.longitude == null) {
            Toast.makeText(applicationContext, "Select the course location on map", Toast.LENGTH_LONG).show()
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        addLocation.setOnClickListener {
            runBlocking(coroutineContext) {
                course.latitude = mLocation.latitude
                course.longitude = mLocation.longitude
                AppDatabase.getInstance(applicationContext).getCourseDAO().update(course)
            }
            Toast.makeText(applicationContext, "Location added! Latitude: " + course.latitude + " Longitude: " + course.longitude, Toast.LENGTH_SHORT).show()
        }

        switchMapType.setOnClickListener {
            if (mMap.mapType == MAP_TYPE_NORMAL) mMap.mapType = MAP_TYPE_SATELLITE
            else mMap.mapType = MAP_TYPE_NORMAL
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMapClickListener(this)

        setUpMap()
    }
}

// Reference complete