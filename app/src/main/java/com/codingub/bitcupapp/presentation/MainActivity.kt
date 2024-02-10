package com.codingub.bitcupapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.databinding.ActivityMainBinding
import com.codingub.bitcupapp.presentation.features.home.ui.HomeFragment
import com.codingub.bitcupapp.utils.AnimationUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainActivityViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var Instance: MainActivity? = null
        fun getInstance(): MainActivity = Instance!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                vm.isLoading.value
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        Instance = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        hideSystemUI()
        createBottomBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        //   navController = null
        binding = null
    }

    /*
        UI
     */

    private fun createBottomBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        binding!!.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_tab, R.id.home_fragment -> {
                    if (!binding!!.bottomNav.isVisible) {
                        AnimationUtil.animateNavBar(binding!!.bottomShadow, true)
                        AnimationUtil.animateNavBar(binding!!.bottomNav, true)
                    }
                }

                R.id.details_fragment -> {
                    AnimationUtil.animateNavBar(binding!!.bottomShadow, false)
                    AnimationUtil.animateNavBar(binding!!.bottomNav, false)
                }

                R.id.bookmarks_tab, R.id.bookmarks_fragment -> {
                    if (!binding!!.bottomNav.isVisible) {
                        AnimationUtil.animateNavBar(binding!!.bottomShadow, true)
                        AnimationUtil.animateNavBar(binding!!.bottomNav, true)
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding!!.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}