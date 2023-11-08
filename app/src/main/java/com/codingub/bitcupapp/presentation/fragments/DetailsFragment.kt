package com.codingub.bitcupapp.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.databinding.FragmentDetailsBinding
import com.codingub.bitcupapp.databinding.FragmentHomeBinding
import com.codingub.bitcupapp.presentation.SharedViewModel
import com.codingub.bitcupapp.presentation.viewmodels.DetailsViewModel
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val vm: DetailsViewModel by viewModels()
    private val model: SharedViewModel by activityViewModels()

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inf, con, false)

        createUI()
        setupListeners()
        observeChanges()
        return binding.root
    }

    private fun createUI(){
        binding.tvPhotographer.typeface = Font.REGULAR
    }

    private fun setupListeners(){

        binding.back.setOnClickListener{
            pushFragment(HomeFragment(), "home")
        }
        binding.bookmark.setOnClickListener {

        }
        binding.download.setOnClickListener{
            Log.d("Download", "Clicked")

            ImageUtil.loadBitmapFromUri(Uri.parse(vm.photo.value!!.data!!.photoSrc.large2x), requireContext()){
                ImageUtil.saveBitmapAsImageToDevice(requireContext(),it)
            }
        }

    }

    override fun observeChanges() {
        with(model){
            vm.getPhotoInfo(photoId.value!!)
        }
        with(vm){
            photo.observe(viewLifecycleOwner) {

                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }

                    is ResultState.Success -> {

                        binding.tvPhotographer.text = it.data?.photographer ?: ""

                        ImageUtil.load(Uri.parse(it.data?.photoSrc!!.large2x)) {
                            binding.imgPhoto.imgPhoto.apply {
                                setImageDrawable(it)
                            }
                        }

                        showResults()
                    }

                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), it.error?.message.toString(), Toast.LENGTH_LONG)
                        showNetworkError()
                    }
                }
            }
        }
    }

    private fun showLoading(){

    }

    private fun showResults() {
    }

    private fun showNetworkError(){

    }
}