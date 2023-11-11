package com.codingub.bitcupapp.presentation.fragments


import android.graphics.Outline
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.databinding.FragmentHomeBinding
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.presentation.MainActivityViewModel
import com.codingub.bitcupapp.presentation.SharedViewModel
import com.codingub.bitcupapp.presentation.adapters.CuratedPhotoAdapter
import com.codingub.bitcupapp.presentation.custom.FeaturedView
import com.codingub.bitcupapp.presentation.viewmodels.HomeViewModel
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.ItemDecoration
import com.codingub.bitcupapp.utils.Resource
import com.codingub.bitcupapp.utils.extension.dp
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val vm: HomeViewModel by viewModels()
    private val activityVm: MainActivityViewModel by activityViewModels()
    private val model: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var featuredCollections: List<FeaturedCollection>
    private val tabs: MutableList<TabLayout.Tab> = mutableListOf()
    private lateinit var photoAdapter: CuratedPhotoAdapter


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inf, con, false)

        //ui
        customizeUI()
        createTabLayout()
        createPhotoContainerView()

        return binding.root
    }

    override fun viewCreated() {
        observeChanges()
        setupListeners()

        vm.lastRequestedAction?.invoke() ?: vm.updateData()
    }


    override fun onResume() {
        super.onResume()
        vm.lastRequestedAction?.invoke() ?: vm.updateData()
    }

    private fun customizeUI() {
        binding.llNetwork.tvTryAgain.typeface = Font.BOLD
        binding.etSearch.typeface = Font.REGULAR
        binding.llNotFound.tvNoResult.apply {
            typeface = Font.REGULAR
            text = Resource.string(R.string.no_results_found)
        }
        binding.llNotFound.tvExplore.typeface = Font.REGULAR
    }

    private fun createTabLayout() {


        binding.Collections.apply {
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
    }

    private fun createPhotoContainerView() {
        binding.photoContainerView.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            photoAdapter = CuratedPhotoAdapter {
                model.setPhotoId(it.id)
                pushFragment(DetailsFragment(), "details")
            }
            adapter = photoAdapter
            addItemDecoration(ItemDecoration.createItemDecoration(8))
        }
    }

    private fun setupListeners() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.imgClear.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        vm.searchPhoto(s.toString())
                        vm.updateLastRequestedAction {
                            vm.searchPhoto(s.toString())
                        }
                    }
                } else {
                    binding.imgClear.visibility = View.GONE
                }

                val collections =
                    vm.getCollectionsLiveData().value?.data?.map { it.title } ?: return
                val matchingIndex = collections.indexOfFirst { it == s.toString() }

                if (matchingIndex != -1) {
                    binding.Collections.getTabAt(matchingIndex)?.select()
                } else {
                    binding.Collections.selectTab(null)
                }

            }
        })

        binding.etSearch.setOnClickListener {
            binding.etSearch.text = null
        }


        binding.etSearch.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = textView.text.toString()
                vm.searchPhoto(searchText)
                vm.updateLastRequestedAction {
                    vm.searchPhoto(searchText)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        binding.imgClear.setOnClickListener {
            if (binding.etSearch.text?.isNotEmpty() == true) binding.etSearch.text = null

        }

        binding.Collections.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                val index = tabs.indexOf(tab)
                if (index == -1) return
                val categoryView = tab.customView as FeaturedView
                categoryView.setChecked(true, animated = true)


                binding.etSearch.setText(categoryView.text)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.customView as FeaturedView).setChecked(false, animated = true)

            }

            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })

        binding.llNetwork.tvTryAgain.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                vm.lastRequestedAction?.invoke() ?: vm.updateData()
            }
        }

        binding.llNotFound.tvExplore.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                vm.getCuratedPhotos()
                vm.updateLastRequestedAction {
                    vm.getCuratedPhotos()
                }

            }
        }


    }

    override fun observeChanges() {

        with(vm) {
            getCollectionsLiveData().observe(viewLifecycleOwner) { collection ->
                when (collection) {
                    is ResultState.Loading -> showLoading()

                    is ResultState.Success -> {
                        featuredCollections = collection.data ?: emptyList()
                        featuredCollections.forEachIndexed { _, category ->
                            binding.Collections.apply {
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

                        if (collection.data.isNullOrEmpty()) {
                            binding.llNetwork.llNetwork.visibility = View.VISIBLE
                        }

                        Toast.makeText(
                            requireContext(),
                            collection.error?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            getPhotosLiveData().observe(viewLifecycleOwner) {

                when (it) {
                    is ResultState.Loading -> showLoading()

                    is ResultState.Success -> {
                        photoAdapter.photos = it.data ?: emptyList()
                        photoAdapter.notifyItemRangeChanged(0, photoAdapter.itemCount)

                        showSuccess()
                    }

                    is ResultState.Error -> {
                        showError()

                        if (it.data.isNullOrEmpty()) {
                            binding.llNetwork.llNetwork.visibility = View.VISIBLE
                        }

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
        lifecycleScope.launch(Dispatchers.Main) {
            delay(500)
            binding.llNetwork.llNetwork.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.shimmer.visibility = View.GONE
            binding.photoContainerView.visibility = View.VISIBLE

            if (vm.getCollectionsLiveData().value is ResultState.Success && binding.Collections.visibility == View.GONE) {
                binding.Collections.visibility = View.VISIBLE
            }

            if (vm.getPhotosLiveData().value?.data.isNullOrEmpty()
                && binding.Collections.visibility == View.VISIBLE
            ) {
                binding.llNotFound.llNotFound.visibility = View.VISIBLE
            }
            showScreen()
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.shimmer.visibility = View.VISIBLE


        binding.llNetwork.llNetwork.visibility = View.GONE
        binding.photoContainerView.visibility = View.GONE
        binding.Collections.visibility = View.GONE
        binding.llNotFound.llNotFound.visibility = View.GONE

    }

    private fun showError() {
        if (!vm.getCollectionsLiveData().value?.data.isNullOrEmpty()) {
            binding.Collections.visibility = View.VISIBLE
        }

        binding.progressBar.visibility = View.VISIBLE


        binding.shimmer.visibility = View.GONE
        binding.llNotFound.llNotFound.visibility = View.GONE
        binding.photoContainerView.visibility = View.GONE
        showScreen()
    }

    private fun showScreen() {
        if (activityVm.isLoading.value) activityVm.isLoading.value = false
    }

}