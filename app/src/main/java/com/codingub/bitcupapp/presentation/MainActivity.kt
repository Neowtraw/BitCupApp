package com.codingub.bitcupapp.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.databinding.ActivityMainBinding
import com.codingub.bitcupapp.presentation.fragments.BookmarksFragment
import com.codingub.bitcupapp.presentation.fragments.DetailsFragment
import com.codingub.bitcupapp.presentation.fragments.HomeFragment
import com.codingub.bitcupapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    //used for navigation
    private val TIME_INTERVAL: Long = 2000
    private var mBackPressedTime: Long = 0

    /**
     * Used for activity context
     */
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
        val view = binding.root
        setContentView(view)

        hideSystemUI()

        createBottomBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, HomeFragment())
                .commit()
        }

        onBackPressedDispatcher.addCallback(this) {
            back()
        }
    }

    /*
        UI
     */

    private fun createBottomBar() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_tab -> pushFragment(HomeFragment(), "home")
                R.id.bookmark_tab -> pushFragment(BookmarksFragment(), "bookmark")
                else -> {}
            }
            true
        }

    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            binding.root
        ).show(WindowInsetsCompat.Type.systemBars())
    }

    /*
        Navigation
     */

    fun pushFragment(fragment: BaseFragment, backstack: String?) {
        if (fragment is DetailsFragment) {
            binding.bottomNavigationView.visibility = View.GONE
        } else {
            binding.bottomNavigationView.visibility = View.VISIBLE
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.addToBackStack(backstack)


        fragmentTransaction.commit()
    }

    fun back() {

        if (supportFragmentManager.fragments.last() is BookmarksFragment) {
            return
        }
        if (supportFragmentManager.fragments.last() is DetailsFragment) {
            binding.bottomNavigationView.visibility = View.VISIBLE
        }

        if (supportFragmentManager.fragments.last() is HomeFragment) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mBackPressedTime > TIME_INTERVAL) {
                Toast.makeText(this@MainActivity, R.string.exit_app_string, Toast.LENGTH_SHORT)
                    .show()
                mBackPressedTime = currentTime
            } else {
                finish()
            }
            return
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        currentFragment?.view?.startAnimation(
            AnimationUtils.loadAnimation(
                this@MainActivity,
                R.anim.fade_out
            )
        )
        supportFragmentManager.popBackStack()
        transaction.commit()

    }
}