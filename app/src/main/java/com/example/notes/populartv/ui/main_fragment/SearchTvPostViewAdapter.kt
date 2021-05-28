package com.example.notes.populartv.ui.main_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.populartv.R
import com.example.notes.populartv.models.SearchTvPost
import com.example.notes.populartv.utilits.POSTER_W200_BASE_URL
import com.example.notes.populartv.utilits.showYear
import com.squareup.picasso.Picasso

class SearchTvPostViewAdapter(private val cl: SearchTvPostClickListener) :
    PagingDataAdapter<SearchTvPost, SearchTvPostViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(getItem(position)!!, cl.clickListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.film_item, parent, false)

        return MyViewHolder(inflater)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName: TextView = view.findViewById(R.id.name_list)
        private val tvVoteRb: RatingBar = view.findViewById(R.id.vote_average_list)
        private val tvVote: TextView = view.findViewById(R.id.vote_average_number_list)
        private val tvPoster: ImageView = view.findViewById(R.id.poster_image_list)

        @SuppressLint("SetTextI18n")
        fun bind(data: SearchTvPost, clickListener: (tvPost: SearchTvPost) -> Unit) {
            itemView.setOnClickListener {clickListener(data)}
            tvName.text = data.name + " (" + data.firstAirDate?.let { showYear(it) } + ")"
            tvVoteRb.rating = data.voteAverage.toFloat() / 2
            tvVote.text = data.voteAverage.toString()

            Picasso.get()
                .load(POSTER_W200_BASE_URL + data.posterPath)
                .into(tvPoster)
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<SearchTvPost>() {
        override fun areItemsTheSame(oldItem: SearchTvPost, newItem: SearchTvPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchTvPost, newItem: SearchTvPost): Boolean {
            return oldItem == newItem
        }
    }

    data class SearchTvPostClickListener(val clickListener: (tvPost: SearchTvPost) -> Unit)
}