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
import com.joaquinco.androidavanzado.domain.Location
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
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

        setListeners()
    }

    private fun observeViewModel() {

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is DetailState.Success -> {
                    binding.tvHeroName.text = it.hero.name
                    binding.tvDescription.text = it.hero.description
                    binding.ivHero.load(it.hero.photo)

                    if (it.hero.favorite) {
                        binding.ivHeart.setImageResource(R.drawable.red_heart)
                    } else {
                        binding.ivHeart.setImageResource(R.drawable.white_heart)
                    }

                }
                else -> {
                    Toast.makeText(requireContext(), "Error: ", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.locations.observe(viewLifecycleOwner) {
            loadLocationsInMap(it)
        }

        viewModel.likeState.observe(viewLifecycleOwner){
            when (it){
                is LikeState.Success -> Toast.makeText(requireContext(), "Favorito actualizado con éxito", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(requireContext(), "Error en actualizado favorito", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setListeners() {

        binding.ivHeart.setOnClickListener {
            viewModel.setLike()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadLocationsInMap(locations: List<Location>) {
        if (this::googleMap.isInitialized){
            for (location in locations) {
                val latlng = LatLng(location.latitud, location.longitud)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latlng)
                        .title(args.heroName)
                )
            }
            if (locations.isNotEmpty()) {
                val lastCoordenate = LatLng(locations.last().latitud, locations.last().longitud)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastCoordenate, 2f))
            }
        }

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        //Listeners que permiten poder moverse dentro del mapa estando dentro del Scroll View
        googleMap.setOnCameraMoveStartedListener {
            mapFragment?.view?.parent?.requestDisallowInterceptTouchEvent(true)
        }
        googleMap.setOnCameraIdleListener {
            mapFragment?.view?.parent?.requestDisallowInterceptTouchEvent(false)
        }

        googleMap.uiSettings.isZoomControlsEnabled = true
    }
}
