package com.codingub.bitcupapp.presentation.fragments


import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.common.FeaturedType
import com.codingub.bitcupapp.databinding.FragmentHomeBinding
import com.codingub.bitcupapp.presentation.custom.FeaturedView
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.AssetUtil
import com.codingub.bitcupapp.utils.ImageUtil
import com.codingub.bitcupapp.utils.Resource
import com.codingub.bitcupapp.utils.extension.dp
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var achieveView: RecyclerView
    private lateinit var categoriesLayout: TabLayout

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inf, con, false)

        //ui
        customizeSearch()
        createTabLayout()

        observeChanges()
        return binding.root
    }

    private fun customizeSearch() {
        binding.Search.apply {
            post {
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

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val categoryView = tab.customView as FeaturedView
                    categoryView.setChecked(true, animated = true)
                    lifecycleScope.launch(Dispatchers.IO) {
                        //  vm.updateAchieves(categoryView.category)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    (tab.customView as FeaturedView).setChecked(false, animated = true)
                }

                override fun onTabReselected(tab: TabLayout.Tab) = Unit
            })

        }

        val categories = FeaturedType.values()
        categories.forEachIndexed { index, category ->
            categoriesLayout.apply {
                newTab().apply {
                    customView = FeaturedView(requireContext(), category)
                    view.setPadding(8.dp, 0, 8.dp, 0)
                }.also {
                    addTab(it)
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


}