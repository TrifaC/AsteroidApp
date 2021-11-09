package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.isNetworkAvailable
import com.udacity.asteroidradar.data.AsteroidAPIFilter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.processNextEventInCurrentThread

class MainFragment : Fragment() {

    companion object {
        private const val LOG_TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var application: Application
    private lateinit var adapter: AsteroidAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private var currentFilter: AsteroidAPIFilter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        application = requireNotNull(this.activity).application
        initBindingAndVM()
        initAdapter()
        initVMConnection()
        setHasOptionsMenu(true)
        return binding.root
    }


//************************************* Initialization *********************************************


    /**
     * Data Binding and View Model.
     * */
    private fun initBindingAndVM() {
        viewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initAdapter() {
        adapter = AsteroidAdapter(AsteroidItemClickListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecyclerView.adapter = adapter
    }

    private fun initVMConnection() {
        viewModel.responseAsteroidList.observe(
            viewLifecycleOwner,
            Observer { updateShowingList() })
        viewModel.asteroidShowingList.observe(
            viewLifecycleOwner,
            Observer { adapter.submitList(it) }
        )
        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.view?.findNavController()
                    ?.navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.doneNavigation()
            }
        })
        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer { picture ->
            picture?.let {
                //Picasso.with(context).load(picture.url).into(binding.activityMainImageOfTheDay)
                Picasso.get().load(picture.url).into(binding.activityMainImageOfTheDay)
            }
        })
        viewModel.stateInfoShowing.observe(viewLifecycleOwner, Observer { stateInfo ->
            stateInfo?.let {
                Toast.makeText(context, stateInfo, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateShowingList() {
        viewModel.refreshListData(currentFilter)
    }


//************************************* Menu Functions *********************************************


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu_item -> viewModel.refreshListData(AsteroidAPIFilter.WEEK)
            R.id.show_today_menu_item -> viewModel.refreshListData(AsteroidAPIFilter.TODAY)
            else -> viewModel.refreshListData(AsteroidAPIFilter.SAVED)
        }
        return true
    }

}
