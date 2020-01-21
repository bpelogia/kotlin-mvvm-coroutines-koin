package com.maiconhellmann.architecture.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maiconhellmann.architecture.R
import com.maiconhellmann.architecture.misc.ext.gone
import com.maiconhellmann.architecture.misc.ext.observe
import com.maiconhellmann.architecture.misc.ext.visible
import com.maiconhellmann.architecture.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie.recyclerView
import kotlinx.android.synthetic.main.fragment_movie.textViewError
import kotlinx.android.synthetic.main.fragment_movie.viewError
import kotlinx.android.synthetic.main.fragment_movie.viewProgressBar
import org.koin.android.architecture.ext.viewModel

class SeriesFragment : BaseFragment() {

    val viewModel: MainViewModel by viewModel()

    private var adapter: MovieAdapter = MovieAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        //Current currency
        observe(viewModel.series) {
            it?.let {
                adapter.dataList = it
            }
            showNoDataFound(adapter.dataList.isEmpty())
        }

        //ProgressBar
        observe(viewModel.isDataLoading) {
            if (it == true) {
                viewProgressBar.visible()
            } else {
                viewProgressBar.gone()
            }
        }

        observe(viewModel.exception) {
            showErrorMessage(it?.message)
        }
    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            //TODO dialog
//            alert(message, getString(R.string.error)) {
//                yesButton { }
//            }.show()
            viewModel.exception.value = null
        }
    }

    private fun showNoDataFound(show: Boolean) {
        if (show) {
            viewError.visible()
            textViewError.text = getString(R.string.no_series_found)
        } else {
            viewError.gone()
        }
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
    }
}