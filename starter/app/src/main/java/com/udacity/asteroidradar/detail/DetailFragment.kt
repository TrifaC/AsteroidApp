package com.udacity.asteroidradar.detail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

/**
 * The class contain the fragment UI of the Asteroid detail.
 * */
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        initBinding()
        initClickListener()
        return binding.root
    }


//************************************* Initialization *********************************************

    private fun initBinding() {
        // Connect the class with XML UI.
        binding.lifecycleOwner = this

        // Get the Data from the list fragment.
        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).selectedAsteroid

        // asteroid is the data binding in the detail fragment.
        binding.asteroid = asteroid
    }

    private fun initClickListener() {
        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
    }


    // The function is usd to pop up a dialog.
    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
