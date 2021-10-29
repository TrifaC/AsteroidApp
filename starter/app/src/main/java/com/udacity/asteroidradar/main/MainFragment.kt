package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: AsteroidAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initBinding(inflater)
        initAdapter()
        setHasOptionsMenu(true)
        return binding.root
    }


//************************************* Initialization *********************************************


    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initAdapter() {
        adapter = AsteroidAdapter()
        binding.asteroidRecyclerView.adapter = adapter
        // TODO: Set the observe function for value changing the Asteroid List.
        // Hard code the data here.
        adapter.asteroidListData = createDefaultAsteroidList()
    }


//************************************* Menu Functions *********************************************


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

//************************************* Test Function **********************************************

    private fun createDefaultAsteroidList(): ArrayList<Asteroid> {
        val asteroidItem1 = Asteroid(1, "1.1", "1.1.1", 1.0, 1.0, 1.0, 1.0, true)
        val asteroidItem2 = Asteroid(2, "2.1", "2.1.1", 1.0, 1.0, 1.0, 1.0, false)
        val asteroidItem3 = Asteroid(3, "3.1", "3.1.1", 1.0, 1.0, 1.0, 1.0, true)
        val asteroidItem4 = Asteroid(4, "4.1", "4.1.1", 1.0, 1.0, 1.0, 1.0, false)
        var defaultList = ArrayList<Asteroid>()
        defaultList.add(asteroidItem1)
        defaultList.add(asteroidItem2)
        defaultList.add(asteroidItem3)
        defaultList.add(asteroidItem4)
        return defaultList
    }
}
