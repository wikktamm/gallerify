package com.example.gallerify.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerify.models.LabelledImage

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(private val item: View) : RecyclerView.ViewHolder(item)

    val diffUtil = object : DiffUtil.ItemCallback<LabelledImage>() {
        override fun areItemsTheSame(oldItem: LabelledImage, newItem: LabelledImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LabelledImage, newItem: LabelledImage): Boolean {
            return oldItem.uid == newItem.uid
        }
    }

    val diffUtilCallback = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}