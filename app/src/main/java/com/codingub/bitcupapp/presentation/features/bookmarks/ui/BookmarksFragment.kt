package com.codingub.bitcupapp.presentation.features.bookmarks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.databinding.FragmentBookmarksBinding
import com.codingub.bitcupapp.presentation.SharedViewModel
import com.codingub.bitcupapp.presentation.features.bookmarks.vm.BookmarksViewModel
import com.codingub.bitcupapp.presentation.features.details.ui.DetailsFragment
import com.codingub.bitcupapp.presentation.features.home.ui.HomeFragment
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ItemDecoration
import com.codingub.bitcupapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksFragment : BaseFragment() {

    private val vm: BookmarksViewModel by viewModels()
    private val model: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var bookmarksAdapter: BookmarkPhotoAdapter

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentBookmarksBinding.inflate(inf, con, false)
        vm.getBookmarkPhotos()

        customizeUI()
        customizeBookmarksView()
        setupListeners()

        observeChanges()
        return binding.root
    }

    private fun customizeUI() {

        binding.tvBookmarks.typeface = Font.REGULAR
        binding.tvTryAgain.typeface = Font.BOLD
        binding.llNotFound.tvNoResult.apply {
            typeface = Font.REGULAR
            text = Resource.string(R.string.no_bookmarks_found)
        }
        binding.llNotFound.tvExplore.typeface = Font.REGULAR
        binding.llNotFound.llNotFound.visibility = View.GONE
    }

    private fun customizeBookmarksView() {
        binding.rvBookmarksView.apply {
            visibility = View.GONE
            setHasFixedSize(true)
            bookmarksAdapter = BookmarkPhotoAdapter {
                model.setPhotoId(it.id)
                pushFragment(DetailsFragment(), "details")
            }
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = bookmarksAdapter
            addItemDecoration(ItemDecoration.createItemDecoration(8))
        }
    }

    private fun setupListeners() {
        binding.llNotFound.tvExplore.setOnClickListener {
            pushFragment(HomeFragment(), "home")
        }
    }


    override fun observeChanges() {
        lifecycleScope.launch {
            requireActivity().lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.bookmarks.collect {
                    binding.rvBookmarksView.visibility = View.VISIBLE
                    bookmarksAdapter.photos = it
                    bookmarksAdapter.notifyItemChanged(0, it.size)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getBookmarkPhotos()
    }


}