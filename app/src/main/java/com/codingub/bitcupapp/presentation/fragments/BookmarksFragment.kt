package com.codingub.bitcupapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.databinding.FragmentBookmarksBinding
import com.codingub.bitcupapp.presentation.adapters.BookmarkPhotoAdapter
import com.codingub.bitcupapp.presentation.viewmodels.BookmarksViewModel
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ItemDecoration
import com.codingub.bitcupapp.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : BaseFragment() {

    private val vm: BookmarksViewModel by viewModels()
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var bookmarksAdapter: BookmarkPhotoAdapter

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentBookmarksBinding.inflate(inf, con, false)

        customizeUI()
        customizeBookmarksView()

        observeChanges()
        return binding.root
    }

    private fun customizeUI() {

        binding.tvBookmarks.typeface = Font.REGULAR
        binding.tvTryAgain.typeface = Font.BOLD
        binding.tvNoBookmarks.typeface = Font.REGULAR
        binding.tvExplore.typeface = Font.REGULAR
    }

    private fun customizeBookmarksView() {
        binding.rvBookmarksView.apply {
            visibility = View.GONE
            setPadding(22.dp, 0, 22.dp, 0)
            setHasFixedSize(true)
            bookmarksAdapter = BookmarkPhotoAdapter {
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


    override fun observeChanges() {
        with(vm) {
            getBookmarksLiveData().observe(viewLifecycleOwner) {
                bookmarksAdapter.photos = it ?: emptyList()
                bookmarksAdapter.notifyDataSetChanged()

                if(bookmarksAdapter.photos.isEmpty()) {
                    binding.llEmpty.visibility = View.VISIBLE
                    return@observe
                }
                binding.tvBookmarks.visibility = View.VISIBLE
            }
        }
    }


}