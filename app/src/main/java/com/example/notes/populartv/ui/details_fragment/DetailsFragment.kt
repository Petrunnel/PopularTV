package com.example.notes.populartv.ui.details_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes.populartv.MainActivity
import com.example.notes.populartv.R
import com.example.notes.populartv.databinding.FragmentDetailsBinding
import com.example.notes.populartv.utilits.APP_ACTIVITY
import com.example.notes.populartv.utilits.POSTER_W500_BASE_URL
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : MvpAppCompatFragment(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenter

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!
    private var tvId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        tvId = arguments?.getInt("id")!!
        val mainActivity = activity as MainActivity
        mainActivity.mToolbar.setNavigationIcon(R.drawable.ic_back)
        mainActivity.mToolbar.setNavigationOnClickListener {
            mainActivity.mToolbar.navigationIcon = null
            mainActivity.mToolbar.title = getString(R.string.toolbar_title_default)
            activity?.onBackPressed()
        }
        mainActivity.mToolbar.title = getString(R.string.toolbar_title_details)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        showDetails()
    }

    @SuppressLint("SetTextI18n")
    override fun showDetails() {
        val seasons = APP_ACTIVITY.resources.getString(R.string.seasons)
        val episodes = APP_ACTIVITY.resources.getString(R.string.episodes)
        val firstAirDate = APP_ACTIVITY.resources.getString(R.string.first_air_date)
        val lastAirDate = APP_ACTIVITY.resources.getString(R.string.last_air_date)
        val averageVote = APP_ACTIVITY.resources.getString(R.string.average_vote)
        val status = APP_ACTIVITY.resources.getString(R.string.status)

        val response = presenter.getTvPostDetails(tvId)
        mBinding.nameDetails.text = response.name
        mBinding.tagLineDetails.text = response.tagline
        mBinding.numberOfSeasonsDetails.text = seasons + " " + response.numberOfSeasons.toString()
        mBinding.numberOfEpisodesDetails.text =
            episodes + " " + response.numberOfEpisodes.toString()
        mBinding.firstAirDateDetails.text = firstAirDate + " " + response.firstAirDate
        mBinding.lastAirDateDetails.text = lastAirDate + " " + response.lastAirDate
        mBinding.voteAverageDetails.text = averageVote + " " + response.voteAverage.toString()
        mBinding.statusDetails.text = status + " " + response.status
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