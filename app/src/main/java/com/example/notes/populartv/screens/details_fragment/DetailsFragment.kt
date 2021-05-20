package com.example.notes.populartv.screens.details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.populartv.R
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.models.TvPostDetails
import com.example.notes.populartv.databinding.FragmentDetailsBinding
import com.example.notes.populartv.di.NetworkModule
import com.example.notes.populartv.utilits.API_KEY
import com.example.notes.populartv.utilits.POSTER_W500_BASE_URL
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DetailsFragment : Fragment() {

    private val api: PopularTvApi = NetworkModule.providePopularTvApi()
    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!
    private var tvId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        tvId = arguments?.getInt("id")!!
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        fun details(): TvPostDetails {
            return runBlocking(Dispatchers.IO) {
                api.getDetails(id = tvId, apiKey = API_KEY)
            }
        }

        val response = details()

        mBinding.nameDetails.text = response.name
        mBinding.firstAirDateDetails.text = "First date air: " + response.firstAirDate
        mBinding.voteAverageDetails.text = "Average vote: " + response.voteAverage.toString()
        mBinding.overviewDetails.text = response.overview

        Picasso.get()
            .load(POSTER_W500_BASE_URL + response.posterPath)
            .into(mBinding.posterImageDetails)

        Picasso.get()
            .load(POSTER_W500_BASE_URL + response.backdropPath)
            .resizeDimen(R.dimen.picture_details_weight, R.dimen.picture_details_weight)
            .centerInside()
            .into(mBinding.backdropPathDetails)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}