package com.codingub.bitcupapp.presentation.fragments


import android.graphics.Outline
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.databinding.FragmentHomeBinding
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.presentation.SharedViewModel
import com.codingub.bitcupapp.presentation.adapters.CuratedPhotoAdapter
import com.codingub.bitcupapp.presentation.custom.FeaturedView
import com.codingub.bitcupapp.presentation.viewmodels.HomeViewModel
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.AssetUtil
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ImageUtil
import com.codingub.bitcupapp.utils.ItemDecoration
import com.codingub.bitcupapp.utils.Resource
import com.codingub.bitcupapp.utils.extension.dp
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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
    private val tabs: MutableList<TabLayout.Tab> = mutableListOf()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inf, con, false)

        vm.getCollections()
        vm.getCuratedPhotos()

        //ui
        customizeUI()
        customizeSearch()
        createTabLayout()
        createPhotoView()

        return binding.root
    }

    override fun viewCreated() {
        observeChanges()
        setupListeners()
    }


    private fun customizeUI() {
        binding.llNetwork.tvTryAgain.typeface = Font.BOLD
        binding.llNotFound.tvNoResult.apply {
            typeface = Font.REGULAR
            text = Resource.string(R.string.no_results_found)
        }
        binding.llNotFound.tvExplore.typeface = Font.REGULAR
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
    }

    private fun createTabLayout() {
        categoriesLayout = TabLayout(requireContext()).apply {
            visibility = View.GONE
            setPadding(22.dp, 24.dp, 22.dp, 0)
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
            setPadding(22.dp, 0, 22.dp, 0)
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
            addItemDecoration(ItemDecoration.createItemDecoration(8))
        }
        binding.root.addView(
            photoContainerView, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
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
                val index = tabs.indexOf(tab)
                if (index == -1) return

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

        binding.llNetwork.tvTryAgain.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                vm.getCollections()
                vm.getCuratedPhotos()

            }
        }

        binding.llNotFound.tvExplore.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
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
                                    selectTab(null)
                                    tabs.add(it)

                                }
                            }

                        }
                        showSuccess()

                    }

                    is ResultState.Error -> {
                        showError()

                        Toast.makeText(
                            requireContext(),
                            it.error?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
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

                        showSuccess()
                    }

                    is ResultState.Error -> {
                        showError()

                        Toast.makeText(
                            requireContext(),
                            it.error?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }

    private fun showSuccess() {
        binding.llNetwork.llNetwork.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        if (vm.getCollectionsLiveData().value is ResultState.Success && categoriesLayout.visibility == View.GONE)
            categoriesLayout.visibility = View.VISIBLE
        if (vm.getPhotosLiveData().value is ResultState.Success && photoContainerView.visibility == View.GONE
            && vm.getPhotosLiveData().value!!.data?.isEmpty() == false
        ) {
            photoContainerView.visibility = View.VISIBLE
        } else {
            binding.llNotFound.llNotFound.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE

        binding.llNetwork.llNetwork.visibility = View.GONE
        photoContainerView.visibility = View.GONE
        categoriesLayout.visibility = View.GONE
        binding.llNotFound.llNotFound.visibility = View.GONE
    }

    private fun showError() {
        binding.llNetwork.llNetwork.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        binding.llNotFound.llNotFound.visibility = View.GONE
        photoContainerView.visibility = View.GONE
        categoriesLayout.visibility = View.GONE
    }
}