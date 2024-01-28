package com.example.hotstuffkotlin.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentOnboardFourBinding
import com.google.android.material.button.MaterialButton

class FourthScreen : Fragment() {

    private var _binding: FragmentOnboardFourBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnboardFourBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboard_viewPager)
        val continueButton = view.findViewById<MaterialButton>(R.id.onboard_four_continue_button)

        continueButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_learn)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}