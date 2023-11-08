package com.codingub.bitcupapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.bitcupapp.databinding.FragmentBookmarksBinding
import com.codingub.bitcupapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : BaseFragment(){

    private lateinit var binding: FragmentBookmarksBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentBookmarksBinding.inflate(inf, con, false)

        observeChanges()
        return binding.root
    }

    override fun observeChanges() {

    }


}