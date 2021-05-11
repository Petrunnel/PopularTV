package com.example.notes.populartv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.utilits.POSTER_W200_BASE_URL
import com.squareup.picasso.Picasso

class TvPostViewAdapter(): PagingDataAdapter<TvPost, TvPostViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(getItem(position)!!)
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

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.name_list)
        val tvVote: TextView = view.findViewById(R.id.vote_average_list)
        val tvFirstAirDate: TextView = view.findViewById(R.id.first_air_date_list)
        val tvPoster: ImageView = view.findViewById(R.id.poster_image_list)

        fun bind(data: TvPost) {
            tvName.text = data.name
            tvVote.text = data.voteAverage.toString()
            tvFirstAirDate.text = data.firstAirDate

            Picasso.get()
                .load(POSTER_W200_BASE_URL + data.posterPath)
                .into(tvPoster)
        }

    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<TvPost>() {
        override fun areItemsTheSame(oldItem: TvPost, newItem: TvPost): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TvPost, newItem: TvPost): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.id == newItem.id

        }
    }

//    override fun onViewAttachedToWindow(holder: MyViewHolder) {
//        holder.itemView.setOnClickListener {
//            MainFragment.click()
//        }
//    }

}