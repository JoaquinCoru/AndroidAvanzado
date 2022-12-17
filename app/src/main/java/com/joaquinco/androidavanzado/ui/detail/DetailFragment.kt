package com.joaquinco.androidavanzado.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.joaquinco.androidavanzado.R
import com.joaquinco.androidavanzado.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args:DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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


        viewModel.getHeroDetail(args.heroName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
