package com.tripfy.livecity2.ui.dondeDormir

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tripfy.livecity2.databinding.FragmentDondeDormirBinding

class DondeDormirFragment : Fragment() {

    private var _binding: FragmentDondeDormirBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDondeDormirBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}