package com.dicoding.bintangpr.clearvis.view.history

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bintangpr.clearvis.R
import com.dicoding.bintangpr.clearvis.data.model.DataItem
import com.dicoding.bintangpr.clearvis.databinding.ItemResultBinding
import com.dicoding.bintangpr.clearvis.utils.setImageUrl
import java.text.SimpleDateFormat


class HistoryAdapter(
    private val data: List<DataItem>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding =
            ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(content: DataItem) {
            with(binding) {
                if (content.status == "Normal") {
                    tvStatus.setTextColor(Color.parseColor("#26F634"))
                } else {
                    tvStatus.setTextColor(Color.parseColor("#FF3333"))
                }
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
                val output: String = formatter.format(parser.parse(content.createdAt))
                ivResult.setImageUrl(
                    itemView.context,
                    "http://34.142.244.51/${content.image}",
                    pbImage,
                    R.drawable.ic_baseline_broken_image_24
                )
                tvDate.text = output
                tvStatus.text = content.status.toString()
            }
        }
    }
}