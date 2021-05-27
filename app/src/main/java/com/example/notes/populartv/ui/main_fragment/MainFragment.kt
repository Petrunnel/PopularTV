package com.example.notes.populartv.ui.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.populartv.R
import com.example.notes.populartv.databinding.FragmentMainBinding
import com.example.notes.populartv.di.TvPostModule
import com.example.notes.populartv.utilits.APP_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var presenter: MainPresenter

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TvPostViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    @ExperimentalPagingApi
    override fun onStart() {
        super.onStart()
        initSwipeToRefresh()
        initRecyclerView()
        initPresenter()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mRecyclerView.adapter = null
    }

    private fun initRecyclerView() {
        mRecyclerView = mBinding.recyclerView
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(APP_ACTIVITY)
            val decoration  = DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            mAdapter = TvPostViewAdapter(TvPostViewAdapter.TvPostClickListener {
                val bundle = Bundle()
                bundle.putInt("id", it.id)
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            })
            adapter = mAdapter

        }

    }

    @ExperimentalPagingApi
    private fun initPresenter() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            presenter.getTvPosts().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun initSwipeToRefresh() {
        mBinding.swipeRefresh.setOnRefreshListener {
            mAdapter.refresh()
            mBinding.swipeRefresh.isRefreshing = false
        }
    }

}