package com.example.notes.populartv.screens.details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.populartv.R
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.databinding.FragmentDetailsBinding
import com.example.notes.populartv.utilits.POSTER_W500_BASE_URL
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var tvPost: TvPost

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        tvPost = arguments?.getSerializable("id") as TvPost
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mBinding.nameDetails.text = tvPost.name
        mBinding.firstAirDateDetails.text = "First date air: " + tvPost.firstAirDate
        mBinding.voteAverageDetails.text = "Average vote: " + tvPost.voteAverage.toString()
        mBinding.overviewDetails.text = tvPost.overview

        Picasso.get()
            .load(POSTER_W500_BASE_URL + tvPost.posterPath)
            .into(mBinding.posterImageDetails)

        Picasso.get()
            .load(POSTER_W500_BASE_URL + tvPost.backdrop_path)
            .resizeDimen(R.dimen.picture_details_weight,R.dimen.picture_details_weight)
            .centerInside()
            .into(mBinding.backdropPathDetails)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}