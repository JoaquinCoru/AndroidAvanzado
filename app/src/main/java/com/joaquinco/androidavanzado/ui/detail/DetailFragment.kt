package com.joaquinco.androidavanzado.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.joaquinco.androidavanzado.R
import com.joaquinco.androidavanzado.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() , OnMapReadyCallback{

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args:DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        viewModel.getHeroDetail(args.heroName)
        viewModel.getHeroLocations(args.heroId)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    fun observeViewModel(){

        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is DetailState.Success -> {
                    binding.tvHeroName.text = it.hero.name
                    binding.tvDescription.text = it.hero.description
                    binding.ivHero.load(it.hero.photo)
                }
                else -> {
                    Toast.makeText(requireContext(),"Error: ", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.locations.observe(viewLifecycleOwner){
            Log.d(javaClass.name,"Localizaciones: $it")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        //Listeners que permiten poder moverse dentro del mapa estando dentro del Scroll View
        googleMap.setOnCameraMoveStartedListener {
            mapFragment?.view?.parent?.requestDisallowInterceptTouchEvent(true)
        }
        googleMap.setOnCameraIdleListener {
            mapFragment?.view?.parent?.requestDisallowInterceptTouchEvent(false)
        }

        googleMap.uiSettings.isZoomControlsEnabled= true
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 0.5f))
    }
}
