package com.joaquinco.androidavanzado.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaquinco.androidavanzado.R
import com.joaquinco.androidavanzado.databinding.FragmentFavoritesBinding
import com.joaquinco.androidavanzado.domain.SuperHero
import com.joaquinco.androidavanzado.ui.commons.SuperHeroListAdapter
import com.joaquinco.androidavanzado.ui.commons.SuperHeroListAdapterCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), SuperHeroListAdapterCallback {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    private val adapter = SuperHeroListAdapter(this)
    private  val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            favoritesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            favoritesList.adapter = adapter

            viewModel.favorites.observe(viewLifecycleOwner){ favoritesList ->
                adapter.submitList(favoritesList)

            }
        }

        viewModel.getFavorites()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHeroClicked(superHero: SuperHero) {

    }

}