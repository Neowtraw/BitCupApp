package com.codingub.bitcupapp.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.bitcupapp.databinding.ItemCuratedPhotoBinding
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.utils.ImageUtil

class CuratedPhotoAdapter(
    private inline val onPhotoSelected: (Photo) -> Unit
) : RecyclerView.Adapter<CuratedPhotoAdapter.ViewHolder>() {

    private lateinit var binding: ItemCuratedPhotoBinding

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


    inner class ViewHolder(private val binding: ItemCuratedPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

            internal fun binding(){
                ImageUtil.load(Uri.parse(photos[bindingAdapterPosition].photoSrc.small)) {
                    binding.imgPhoto.apply {
                        setImageDrawable(it)
                    }
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
            ItemCuratedPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding()
    }
    override fun getItemCount(): Int = photos.size
}