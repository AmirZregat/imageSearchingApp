package com.project.unsplash.ui.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.project.unsplash.R
import com.project.unsplash.databinding.ItemUnsplashPhotoBinding
import com.project.unsplash.models.UnsplashImage

//this adapter for displaying images inside the recyclerView
class UnsplashImagesAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashImage, UnsplashImagesAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val current = getItem(position)
        if (current != null) {
            holder.bind(current)
        }
    }


    inner class ImageViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rowUsername.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item=getItem(position)
                    if(item!=null){
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(image: UnsplashImage) {
            binding.apply {
                //for the image
                Glide.with(itemView)
                    .load(image.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(rowImageView)

                //photographer profile picture
                Glide.with(itemView)
                    .load(image.user.profile_image.small)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                rowUsername.text = image.user.username
                rowDescription.text = image.description
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(image: UnsplashImage)
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashImage>() {
            override fun areItemsTheSame(
                oldItem: UnsplashImage,
                newItem: UnsplashImage
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UnsplashImage,
                newItem: UnsplashImage
            ) =
                oldItem == newItem
        }
    }
}