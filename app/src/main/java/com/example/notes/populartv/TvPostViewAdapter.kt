package com.example.notes.populartv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.populartv.api.TvPost

class TvPostViewAdapter(): PagingDataAdapter<TvPost, TvPostViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: TvPostViewAdapter.MyViewHolder, position: Int) {

        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvPostViewAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)

        return MyViewHolder(inflater)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.TV_name)

        fun bind(data: TvPost) {
            tvName.text = data.name
        }

    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<TvPost>() {
        override fun areItemsTheSame(oldItem: TvPost, newItem: TvPost): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TvPost, newItem: TvPost): Boolean {
            return oldItem.name == newItem.name

        }

    }

}