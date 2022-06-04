package com.dicoding.bintangpr.clearvis.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bintangpr.clearvis.R
import com.dicoding.bintangpr.clearvis.data.model.DataItem
import com.dicoding.bintangpr.clearvis.databinding.ItemResultBinding
import com.dicoding.bintangpr.clearvis.utils.setImageUrl
import org.threeten.bp.format.DateTimeFormatter


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
        fun bind(content: DataItem) {
            with(binding) {

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                val formatted = content.createdAt?.format(formatter)

                ivResult.setImageUrl(
                    itemView.context,
                    content.image,
                    pbImage,
                    R.drawable.ic_baseline_broken_image_24
                )
                tvDate.text = formatted.toString()
                tvStatus.text = content.status.toString()
            }
        }
    }
}