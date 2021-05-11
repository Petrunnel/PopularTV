package com.example.notes.populartv.screens.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.populartv.R
import com.example.notes.populartv.TvPostViewAdapter
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.databinding.FragmentMainBinding
import com.example.notes.populartv.utilits.APP_ACTIVITY
import kotlinx.coroutines.flow.collectLatest

class MainFragment : Fragment() {

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

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        initViewModel()
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
            mAdapter = TvPostViewAdapter()
            adapter = mAdapter

        }

    }

    private fun initViewModel() {
        val viewModel  = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    companion object{
        fun click(note: TvPost){
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_detailsFragment)
        }
    }

}