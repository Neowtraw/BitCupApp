package com.codingub.bitcupapp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codingub.bitcupapp.presentation.MainActivity

/**
 * Base class for all fragments
 */
abstract class BaseFragment : Fragment() {

    private val mainActivity: MainActivity get() = MainActivity.getInstance()
    override fun getContext(): Context = mainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create()
    }

    override fun onCreateView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        return createView(inf, con, state)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyView()
    }

    protected open fun create() {}

    protected abstract fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View

    protected open fun viewCreated() {}
    protected open fun destroyView() {}
    protected open fun observeChanges() {}

    protected fun pushFragment(fragment: BaseFragment, backstack: String?) {
       // mainActivity.pushFragment(fragment, backstack)
    }

    protected fun backFragment() {
     //   mainActivity.back()
    }
}