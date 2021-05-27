package com.example.notes.populartv.ui.main_fragment

import android.util.Log
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
import com.example.notes.populartv.models.TvPost
import com.example.notes.populartv.utilits.POSTER_W200_BASE_URL
import com.example.notes.populartv.utilits.showYear
import com.squareup.picasso.Picasso

class TvPostViewAdapter(private val cl: TvPostClickListener) :
    PagingDataAdapter<TvPost, TvPostViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

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

        fun bind(data: TvPost, clickListener: (tvPost: TvPost) -> Unit) {
            itemView.setOnClickListener {clickListener(data)}
            Log.d("Id", data.id.toString())
            tvName.text = data.name + " (" + showYear(data.firstAirDate) + ")"
            tvVoteRb.rating = data.voteAverage.toFloat() / 2
            tvVote.text = data.voteAverage.toString()

            Picasso.get()
                .load(POSTER_W200_BASE_URL + data.posterPath)
                .into(tvPoster)
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<TvPost>() {
        override fun areItemsTheSame(oldItem: TvPost, newItem: TvPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvPost, newItem: TvPost): Boolean {
            return oldItem == newItem
        }
    }

    data class TvPostClickListener(val clickListener: (tvPost: TvPost) -> Unit)
}