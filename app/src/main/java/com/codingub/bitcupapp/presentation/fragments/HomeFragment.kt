package com.codingub.bitcupapp.presentation.fragments


import android.graphics.Outline
import android.graphics.Rect
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.databinding.FragmentHomeBinding
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.presentation.SharedViewModel
import com.codingub.bitcupapp.presentation.adapters.CuratedPhotoAdapter
import com.codingub.bitcupapp.presentation.custom.FeaturedView
import com.codingub.bitcupapp.presentation.viewmodels.HomeViewModel
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.AssetUtil
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ImageUtil
import com.codingub.bitcupapp.utils.Resource
import com.codingub.bitcupapp.utils.extension.dp
import com.codingub.bitcupapp.utils.extension.isEmptyOrNull
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val vm: HomeViewModel by viewModels()
    private val model: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var categoriesLayout: TabLayout
    private lateinit var photoContainerView: RecyclerView
    private lateinit var photoAdapter: CuratedPhotoAdapter
    private lateinit var featuredCollections: List<FeaturedCollection>

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inf, con, false)

        //ui
        customizeSearch()
        createTabLayout()
        createPhotoView()

        setupListeners()
        observeChanges()
        return binding.root
    }

    private fun customizeSearch() {
        binding.Search.apply {
            post {
                binding.etSearch.typeface = Font.LIGHT
                ImageUtil.load(AssetUtil.imagesImageUri("search")) {
                    binding.imgSearch.apply {
                        setImageDrawable(it)
                        setColorFilter(Resource.color(R.color.contrast))
                    }
                }
            }
        }
        binding.tvTryAgain.typeface = Font.BOLD
    }

    private fun createTabLayout() {
        categoriesLayout = TabLayout(requireContext()).apply {
            visibility = View.GONE
            setPadding(0, 24.dp, 0, 0)
            id = View.generateViewId()
            setBackgroundResource(R.color.background)
            tabMode = TabLayout.MODE_AUTO
            overScrollMode = View.OVER_SCROLL_NEVER
            setSelectedTabIndicator(null)
            tabRippleColor = null
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRect(0, 5.dp, view.width, view.height)
                }
            }

        }

        binding.root.addView(
            categoriesLayout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun createPhotoView() {
        photoContainerView = RecyclerView(requireContext()).apply {
            visibility = View.GONE
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            photoAdapter = CuratedPhotoAdapter() {
                model.setPhotoId(it.id)
                pushFragment(DetailsFragment(), "details")
            }
            adapter = photoAdapter
            addItemDecoration(createItemDecoration())
        }
        binding.root.addView(
            photoContainerView, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun createItemDecoration(): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val spacing = 8.dp
                outRect.apply {
                    left = spacing
                    top = spacing
                    right = spacing
                    bottom = spacing
                }
            }
        }
    }

    private fun setupListeners() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        vm.searchPhoto(s.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.imgSearch.setOnClickListener {
            if (binding.etSearch.text?.isNotEmpty() == true) {
                binding.etSearch.text = null
            }
        }

        categoriesLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                val categoryView = tab.customView as FeaturedView
                categoryView.setChecked(true, animated = true)
                lifecycleScope.launch(Dispatchers.IO) {
                    vm.searchPhoto(categoryView.text.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.customView as FeaturedView).setChecked(false, animated = true)
            }

            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })

        binding.tvTryAgain.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                vm.getCollections()
                vm.getCuratedPhotos()
            }
        }

    }


    override fun observeChanges() {
        with(vm) {
            getCollectionsLiveData().observe(viewLifecycleOwner) {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }

                    is ResultState.Success -> {
                        featuredCollections = it.data ?: emptyList()
                        featuredCollections.forEachIndexed { index, category ->
                            categoriesLayout.apply {
                                newTab().apply {
                                    customView = FeaturedView(requireContext(), category)
                                    view.setPadding(8.dp, 0, 8.dp, 0)
                                }.also {
                                    addTab(it)
                                }
                            }
                        }
                    }
                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), it.error?.message.toString(), Toast.LENGTH_LONG)
                        showNetworkError()
                    }
                }
            }

            getPhotosLiveData().observe(viewLifecycleOwner) {

                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }

                    is ResultState.Success -> {
                        photoAdapter.photos = it.data ?: emptyList()
                        photoAdapter.notifyDataSetChanged()
                        showResults(photoAdapter.photos)

                    }

                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), it.error?.message.toString(), Toast.LENGTH_LONG)
                        showNetworkError()
                    }
                }
            }
        }
    }

    /*
        Visibility
     */

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE

        binding.llNetwork.visibility = View.GONE
        photoContainerView.visibility = View.GONE
        categoriesLayout.visibility = View.GONE
        binding.llEmpty.visibility = View.GONE
    }

    private fun showNetworkError() {

        binding.llNetwork.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        binding.llEmpty.visibility = View.GONE
        photoContainerView.visibility = View.GONE
        categoriesLayout.visibility = View.GONE
    }

    private fun showResults(photos: List<Photo>) {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            binding.llNetwork.visibility = View.GONE
            binding.progressBar.visibility = View.GONE

            if(photos.isEmpty()) {

                binding.llEmpty.visibility = View.VISIBLE
                categoriesLayout.visibility = View.VISIBLE

                return@launch
            }
            categoriesLayout.visibility = View.VISIBLE


            photoContainerView.visibility = View.VISIBLE

        }

    }

}