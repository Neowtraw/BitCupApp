package com.codingub.bitcupapp.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.bitcupapp.databinding.ItemBookmarkPhotoBinding
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ImageUtil

class BookmarkPhotoAdapter(
    private inline val onPhotoSelected: (Photo) -> Unit
) : RecyclerView.Adapter<BookmarkPhotoAdapter.ViewHolder>() {

    private lateinit var binding: ItemBookmarkPhotoBinding

    //list of photos from db
    var photos: List<Photo>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    //DiffUtil
    private val diffCallback = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    inner class ViewHolder(private val binding: ItemBookmarkPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun binding() {
            val photo = photos[bindingAdapterPosition]
            ImageUtil.load(Uri.parse(photo.photoSrc.large)) {
                binding.imgPhoto.apply {
                    setImageDrawable(it)
                }
            }

            binding.tvPhotographer.apply {
                typeface = Font.REGULAR
                text = photo.photographer
            }
        }

        init {
            binding.root.setOnClickListener {
                onPhotoSelected(photos[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemBookmarkPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding()
    }

    override fun getItemCount(): Int = photos.size

}