package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: AsteroidAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initBindingAndVM(inflater)
        initAdapter()
        initVMConnection()
        setHasOptionsMenu(true)
        return binding.root
    }


//************************************* Initialization *********************************************


    private fun initBindingAndVM(inflater: LayoutInflater) {
        binding = FragmentMainBinding.inflate(inflater)
        viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initAdapter() {
        adapter = AsteroidAdapter(AsteroidItemClickListener { id ->
            Toast.makeText(context, "${id}", Toast.LENGTH_LONG).show()
        })
        binding.asteroidRecyclerView.adapter = adapter
        // TODO: Set the observe function for value changing the Asteroid List.
    }

    private fun initVMConnection() {
        viewModel.testAsteroidList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) } )
    }


//************************************* Menu Functions *********************************************


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

}
