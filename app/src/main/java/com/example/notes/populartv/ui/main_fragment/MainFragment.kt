package com.example.notes.populartv.ui.main_fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.populartv.R
import com.example.notes.populartv.databinding.FragmentMainBinding
import com.example.notes.populartv.utilits.APP_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment :
    Fragment(),
    MainView,
    SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    @Inject
    lateinit var presenter: MainPresenter

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TvPostViewAdapter
    private lateinit var mSearchAdapter: SearchTvPostViewAdapter
    private var mQuery: String? = null


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
        setHasOptionsMenu(true)
        initSwipeToRefresh()
        initAdapter()
        initSearchAdapter()
        initRecyclerView()
    }

    @ExperimentalPagingApi
    override fun onResume() {
        super.onResume()
        if (mQuery != null) {
            mRecyclerView.adapter = mSearchAdapter
            loadData()
        } else {
            mRecyclerView.adapter = mAdapter
            loadData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", mQuery)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mRecyclerView.adapter = null
    }

    override fun initRecyclerView() {
        mRecyclerView = mBinding.recyclerView
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(APP_ACTIVITY)
            val decoration =
                DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = mAdapter
        }
    }

    override fun initSearchAdapter() {
        mSearchAdapter = SearchTvPostViewAdapter(
            SearchTvPostViewAdapter.SearchTvPostClickListener {
                val bundle = Bundle()
                bundle.putInt("id", it.id)
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }
        )
    }

    override fun initAdapter() {
        mAdapter = TvPostViewAdapter(TvPostViewAdapter.TvPostClickListener {
            val bundle = Bundle()
            bundle.putInt("id", it.id)
            findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
        })
    }

    @ExperimentalPagingApi
    override fun loadData() {
        if (mQuery != null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                presenter.getTvPosts(mQuery!!).collectLatest {
                    mSearchAdapter.submitData(it)
                }
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                presenter.getTvPosts().collectLatest {
                    mAdapter.submitData(it)
                }
            }
        }
    }

    @ExperimentalPagingApi
    override fun initSwipeToRefresh() {
        mBinding.swipeRefresh.setOnRefreshListener {
            mQuery = null
            mRecyclerView.adapter = mAdapter
            loadData()
            mAdapter.refresh()
            mBinding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_action_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Search"

        // Detect SearchView icon clicks
        searchView.setOnSearchClickListener { setItemsVisibility(menu, searchItem, false) }

        // Detect SearchView close
        searchView.setOnCloseListener {
            setItemsVisibility(menu, searchItem, true)
            false

        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setItemsVisibility(menu: Menu, exception: MenuItem, visible: Boolean) {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item !== exception) item.isVisible = visible
        }
    }

    @ExperimentalPagingApi
    override fun onQueryTextSubmit(query: String?): Boolean {
        mQuery = query
        loadData()
        return true
    }

    @ExperimentalPagingApi
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.trim().isEmpty()) {
            mQuery = null
            mRecyclerView.adapter = mAdapter
            loadData()
            return false
        }
        mQuery = newText
        mRecyclerView.adapter = mSearchAdapter
        loadData()
        return false
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        return true
    }

    @ExperimentalPagingApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                mQuery = null
                mRecyclerView.adapter = mAdapter
                loadData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}