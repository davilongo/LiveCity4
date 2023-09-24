package com.tripfy.livecity2.ui.queVer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tripfy.livecity2.R
import com.tripfy.livecity2.databinding.FragmentQueVerBinding


class QueVerFragment : Fragment() {

    private var _binding: FragmentQueVerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQueVerBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
}