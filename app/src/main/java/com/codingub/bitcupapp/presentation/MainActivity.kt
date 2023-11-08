package com.codingub.bitcupapp.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentTransaction
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.databinding.ActivityMainBinding
import com.codingub.bitcupapp.presentation.fragments.HomeFragment
import com.codingub.bitcupapp.ui.base.BaseFragment
import com.codingub.bitcupapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val vm: MainActivityViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

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

        createBottomBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, HomeFragment())
                .commit()
        }

        back()
    }

    /*
        UI
     */

    private fun createBottomBar(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.home_tab -> pushFragment(HomeFragment(), "home")
                R.id.bookmark_tab -> pushFragment(HomeFragment(), "bookmark")
                else ->{}
            }
            true
        }
    }

    /*
        Navigation
     */

    fun pushFragment(fragment: BaseFragment, backstack: String?){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.apply {
            remove(supportFragmentManager.fragments.last())
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            add(R.id.fragment_container_view, fragment)
        }

        if (backstack != null) {
            fragmentTransaction.addToBackStack(backstack)
        }

        fragmentTransaction.commit()
    }

    private fun back() {
        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - mBackPressedTime > TIME_INTERVAL) {
                    Toast.makeText(this@MainActivity, R.string.exit_app_string, Toast.LENGTH_SHORT)
                        .show()
                    mBackPressedTime = currentTime
                } else {
                    finish()
                }
                return@addCallback
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_back)
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_view)
            currentFragment?.view?.startAnimation(
                AnimationUtils.loadAnimation(
                    this@MainActivity,
                    R.anim.slide_in
                )
            )
            supportFragmentManager.popBackStack()
            transaction.commit()
        }
    }
}