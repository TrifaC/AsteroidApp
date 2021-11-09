package com.udacity.asteroidradar.detail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

/**
 * The class contain the fragment UI of Asteroid's detail
 * */
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container  If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     * */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater)
        initClickListener()
        return binding.root
    }


//************************************* Initialization *********************************************


    /**
     * Function to initialize the binding.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * */
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDetailBinding.inflate(inflater)
        // Connect the class with XML UI.
        binding.lifecycleOwner = this
        // Get the Data from the list fragment.
        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid
        // asteroid is the data binding in the detail fragment.
        binding.asteroid = asteroid
    }

    /**
     * Function to set up click listener.
     * */
    private fun initClickListener() {
        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
    }

    /**
     * Function to show up the explanation dialog.
     * */
    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
