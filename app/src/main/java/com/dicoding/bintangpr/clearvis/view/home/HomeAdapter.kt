package com.dicoding.bintangpr.clearvis.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bintangpr.clearvis.R
import com.dicoding.bintangpr.clearvis.data.model.DataArticle
import com.dicoding.bintangpr.clearvis.data.model.DataItem
import com.dicoding.bintangpr.clearvis.databinding.ItemArtikelsBinding
import com.dicoding.bintangpr.clearvis.databinding.ItemResultBinding
import com.dicoding.bintangpr.clearvis.utils.setImageUrl
import java.text.SimpleDateFormat

class HomeAdapter(
    private val data: List<DataArticle>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val binding =
            ItemArtikelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemArtikelsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(content: DataArticle) {
            with(binding) {


                ivArticle.setImageUrl(
                    itemView.context,
                    content.img,
                    pbImage,
                    R.drawable.ic_baseline_broken_image_24
                )
                tvDate.text = content.postDate + " ago"
                tvAuthor.text = content.postBy
                tvTitle.text = content.title
                itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[adapterPosition]) }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataArticle)
    }
}