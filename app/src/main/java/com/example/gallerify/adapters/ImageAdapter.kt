package com.example.gallerify.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cunoraz.tagview.Tag
import com.example.gallerify.R
import com.example.gallerify.models.LabelledImage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.row_photo.view.*
import kotlinx.coroutines.tasks.await


class ImageAdapter(private val context: Context, private val userUid: String) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
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
        FirebaseStorage.getInstance().reference.child("${userUid}/${item.uid}.jpg").downloadUrl.addOnSuccessListener { uri ->
            Glide.with(context)
                .load(uri)
                .into(view.ivImage)
            onImageLoadedCallback?.invoke()
        }

        item.tags?.let {
            view.labelView.addTags(it.map { x ->
                Tag(x).apply {
//                    layoutColor = R.color.colorPrimaryDark
                    layoutColor = ContextCompat.getColor(context, R.color.colorPrimary)
                }
            })
        }
    }

    private var onImageLoadedCallback: (() -> Unit)? = null

    fun setOnImageLoadedCallback(function: () -> Unit) {
        onImageLoadedCallback = function
    }

}