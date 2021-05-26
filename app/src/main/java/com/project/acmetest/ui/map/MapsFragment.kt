package com.project.acmetest.ui.map

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.project.acmetest.R
import com.project.acmetest.utils.onQueryTextSubmit

const val ARG_ADDRESS = "address"
/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment() {

    lateinit var searchView: SearchView
    lateinit var mapFragment: SupportMapFragment
    lateinit var mGoogleMap: GoogleMap
    var address : String? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mGoogleMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        if(address != null && address!!.isNotBlank()){
            searchView.setQuery(address,true)
            searchView.requestFocus()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        arguments?.let {
            address = it.getString(ARG_ADDRESS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)

        searchView = view.findViewById(R.id.searchView)
        searchView.onQueryTextSubmit{
            val location = it ?: return@onQueryTextSubmit
            findLocation(location)
        }
    }

    /**
     * Find location in Google Map
     *
     * @param location the location to find out.
     */
   private fun findLocation(location: String){
       var addresses = listOf<Address>()
       val geocoder = Geocoder(requireActivity())
       try {
           addresses = geocoder.getFromLocationName(location,1)
       } catch (e: Exception){
           e.printStackTrace()
       }

       if(!addresses.isNullOrEmpty()){
           val address = addresses[0]
           val latLong = LatLng(address.latitude,address.longitude)
           mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong,10F))
       }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
            else -> false
        }
    }
}