package com.example.notes.populartv.repository

import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.models.TvPostDetails
import com.example.notes.populartv.utilits.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TvPostDetailsRepository {

    private val api: PopularTvApi = PopularTvApi.create()



    fun getTvPostDetails(id: Int): TvPostDetails {
        return runBlocking(Dispatchers.IO) {
            api.getDetails(id = id, apiKey = API_KEY)
        }
    }
}
