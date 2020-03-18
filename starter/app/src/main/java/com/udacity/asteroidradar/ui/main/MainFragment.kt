package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    lateinit var mainBinding: FragmentMainBinding
    lateinit var asteroidsAdapter: AsteroidsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentMainBinding.inflate(inflater)
        mainBinding.lifecycleOwner = this
        mainBinding.viewModel = viewModel
        setHasOptionsMenu(true)

        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindObservables()
    }

    private fun initViews() {
        with(mainBinding.asteroidRecycler) {
            setHasFixedSize(true)
            asteroidsAdapter = AsteroidsAdapter {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            }.also {
                adapter = it
            }
        }
    }

    private fun bindObservables() {
        viewModel.asteroidsLiveData().observe(viewLifecycleOwner, Observer {
            asteroidsAdapter.setData(it)
        })

        viewModel.progressLiveData().observe(viewLifecycleOwner, Observer {
            mainBinding.statusLoadingWheel.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.errorLiveData().observe(viewLifecycleOwner, Observer {
            Snackbar.make(mainBinding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.imageOfTheDayLiveData().observe(viewLifecycleOwner, Observer {
            if (it.url?.isNotEmpty() == true)
                Picasso.get().load(it.url)
                    .error(R.drawable.placeholder_picture_of_day)
                    .placeholder(R.drawable.placeholder_picture_of_day)
                    .into(
                        mainBinding.activityMainImageOfTheDay
                    )
            else mainBinding.activityMainImageOfTheDay.setImageResource(R.drawable.placeholder_picture_of_day)
            
            mainBinding.textView.text = it.title
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
