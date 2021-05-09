package com.example.notes.populartv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.notes.populartv.api.MovieApi
import com.example.notes.populartv.databinding.ActivityMainBinding
import com.example.notes.populartv.utilits.API_KEY
import com.example.notes.populartv.utilits.APP_ACTIVITY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getCurrentData() {
        GlobalScope.launch(Dispatchers.IO) {
            val response: Response<MovieApi.ListingResponse> =
                MovieApi.create().getTop(API_KEY, 1).awaitResponse()
            if (response.isSuccessful) {
                val data: MovieApi.ListingResponse = response.body()!!
            }
        }

    }
}