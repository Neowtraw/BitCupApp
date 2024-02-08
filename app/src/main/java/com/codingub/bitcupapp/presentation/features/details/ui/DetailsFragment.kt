package com.codingub.bitcupapp.presentation.features.details.ui

import android.annotation.SuppressLint
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.databinding.FragmentDetailsBinding
import com.codingub.bitcupapp.presentation.SharedViewModel
import com.codingub.bitcupapp.presentation.features.details.vm.DetailsViewModel
import com.codingub.bitcupapp.presentation.features.home.ui.HomeFragment
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.AnimationUtil
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ImageUtil
import com.codingub.bitcupapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    private var binding: FragmentDetailsBinding? = null

    private val vm: DetailsViewModel by viewModels()
    private val model: SharedViewModel by activityViewModels()

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f
    private val originalScale = 1.0f
    private val maxScale = 2.0f
    private var initialPointer1: PointF? = null
    private var initialPointer2: PointF? = null

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = originalScale.coerceAtLeast(scaleFactor.coerceAtMost(maxScale))

            binding!!.Photo.imgPhoto.scaleX = scaleFactor
            binding!!.Photo.imgPhoto.scaleY = scaleFactor

            return true
        }
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inf, con, false)



        createUI()
        setupListeners()
        observeChanges()
        return binding!!.root
    }

    override fun destroyView() {
        super.destroyView()
        binding = null
    }

    private fun createUI() {
        scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())

        binding!!.download.typeface = Font.REGULAR
        binding!!.tvPhotographer.typeface = Font.REGULAR
        binding!!.llNotFound.tvNoResult.apply {
            typeface = Font.REGULAR
            text = Resource.string(R.string.no_image_found)
        }

        binding!!.llNotFound.llNotFound.visibility = View.GONE
        binding!!.llNotFound.tvExplore.typeface = Font.REGULAR
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding!!.Photo.imgPhoto.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    initialPointer1 = PointF(event.getX(0), event.getY(0))
                    initialPointer2 = null
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    initialPointer2 = PointF(event.getX(1), event.getY(1))
                }

                MotionEvent.ACTION_MOVE -> {
                    if (event.pointerCount > 1 && initialPointer1 != null && initialPointer2 != null) {
                        val currentPointer1 = PointF(event.getX(0), event.getY(0))
                        val currentPointer2 = PointF(event.getX(1), event.getY(1))

                        val centerX = (currentPointer1.x + currentPointer2.x) / 2
                        val centerY = (currentPointer1.y + currentPointer2.y) / 2

                        val initialDistance = distance(initialPointer1!!, initialPointer2!!)
                        val currentDistance = distance(currentPointer1, currentPointer2)

                        scaleFactor *= currentDistance / initialDistance
                        scaleFactor =
                            originalScale.coerceAtLeast(scaleFactor.coerceAtMost(maxScale))

                        binding!!.Photo.imgPhoto.pivotX = centerX
                        binding!!.Photo.imgPhoto.pivotY = centerY
                        binding!!.Photo.imgPhoto.scaleX = scaleFactor
                        binding!!.Photo.imgPhoto.scaleY = scaleFactor
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                    initialPointer1 = null
                    initialPointer2 = null
                    binding!!.Photo.imgPhoto.performClick()
                    resetImageScaleWithAnimation()

                }
            }
            scaleGestureDetector?.onTouchEvent(event)
            true
        }

        binding!!.back.setOnClickListener {
            backFragment()
        }
        binding!!.bookmark.setOnClickListener {
            it.startAnimation(AnimationUtil.clickAnimation())
            vm.updateBookmark()
        }
        binding!!.download.setOnClickListener {
            it.startAnimation(AnimationUtil.clickAnimation())

            ImageUtil.loadBitmapFromUri(
                Uri.parse(vm.photo.value.data!!.photoSrc.large2x),
                requireContext()
            ) {
                ImageUtil.saveBitmapAsImageToDevice(requireContext(), it)
            }
        }
        binding!!.llNotFound.tvExplore.setOnClickListener {
            pushFragment(HomeFragment(), "home")
        }
    }

    override fun observeChanges() {
        with(model) {
            vm.getPhotoInfo(photoId.value!!)
        }
        lifecycleScope.launch {
            requireActivity().lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.photo.collectLatest {
                    when (it) {
                        is ResultState.Loading -> {
                            showLoading()
                        }

                        is ResultState.Success -> {

                            binding!!.tvPhotographer.text = it.data?.photographer ?: ""

                            ImageUtil.load(Uri.parse(it.data?.photoSrc!!.large2x)) {
                                binding!!.Photo.imgPhoto.apply {
                                    setImageDrawable(it)
                                }
                            }
                            vm.isBookmark()

                            showResults()
                        }

                        is ResultState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.error?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            showNetworkError()
                        }
                    }
                }
            }
        }


        lifecycleScope.launch {
            vm.isBookmark.collectLatest {
                binding!!.imgBookmark.isActivated = it

                if (it) {
                    binding!!.imgBookmark.setColorFilter(Resource.color(R.color.contrast))
                    return@collectLatest
                }
                binding!!.imgBookmark.setColorFilter(Resource.color(R.color.text_color))
            }
        }

    }


    private fun showLoading() {
        binding!!.llNotFound.llNotFound.visibility = View.GONE
        binding!!.tvPhotographer.visibility = View.GONE
        binding!!.Photo.imgPhoto.visibility = View.GONE
        binding!!.llInfo.visibility = View.GONE

        binding!!.progressBar.visibility = View.VISIBLE
    }

    private fun showResults() {
        binding!!.llNotFound.llNotFound.visibility = View.GONE
        binding!!.progressBar.visibility = View.GONE

        binding!!.tvPhotographer.visibility = View.VISIBLE
        binding!!.Photo.imgPhoto.visibility = View.VISIBLE
        binding!!.llInfo.visibility = View.VISIBLE
    }

    private fun showNetworkError() {
        binding!!.tvPhotographer.visibility = View.GONE
        binding!!.Photo.imgPhoto.visibility = View.GONE
        binding!!.llInfo.visibility = View.GONE
        binding!!.progressBar.visibility = View.GONE

        binding!!.llNotFound.llNotFound.visibility = View.VISIBLE
    }

    /*
        Additional
     */

    private fun resetImageScaleWithAnimation() {
        binding!!.Photo.imgPhoto.animate()
            .scaleX(originalScale)
            .scaleY(originalScale)
            .setDuration(300)
            .start()

        scaleFactor = originalScale
    }

    private fun distance(point1: PointF, point2: PointF): Float {
        val dx = point1.x - point2.x
        val dy = point1.y - point2.y
        return sqrt(dx * dx + dy * dy)
    }
}