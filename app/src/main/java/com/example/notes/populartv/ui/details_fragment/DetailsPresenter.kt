package com.example.notes.populartv.ui.details_fragment

import com.example.notes.populartv.models.TvPostDetails
import com.example.notes.populartv.repository.TvPostDetailsRepository
import com.example.notes.populartv.utilits.API_KEY
import moxy.MvpPresenter

class DetailsPresenter (private val repository: TvPostDetailsRepository) : MvpPresenter<DetailsView>() {

    fun getTvPostDetails(id: Int) : TvPostDetails{
        return repository.getTvPostDetails(id, API_KEY)
    }

}
