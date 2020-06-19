package com.example.gallerify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cunoraz.tagview.Tag
import com.example.gallerify.R
import com.example.gallerify.models.LabelledImage
import kotlinx.android.synthetic.main.row_photo.view.*


class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val diffUtilCallback = object : DiffUtil.ItemCallback<LabelledImage>() {
        override fun areItemsTheSame(oldItem: LabelledImage, newItem: LabelledImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LabelledImage, newItem: LabelledImage): Boolean {
            return oldItem.uid == newItem.uid
        }
    }

    val diffutil = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_photo, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diffutil.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val view = holder.itemView
        val item = diffutil.currentList[position]
//        view.ivImage.load(item.uid)
        item.tags?.let{
            view.labelView.addTags(it.map { x-> Tag(x) })
        }
    }
}