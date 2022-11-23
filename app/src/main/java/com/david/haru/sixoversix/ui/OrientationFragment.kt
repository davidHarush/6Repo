package com.david.haru.sixoversix.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.david.haru.sixoversix.R
import com.david.haru.sixoversix.databinding.FragmentOrientationBinding
import com.david.haru.sixoversix.util.autoCleared


class OrientationFragment : Fragment(R.layout.fragment_orientation) {

    private var binding by autoCleared<FragmentOrientationBinding>()

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrientationBinding.bind(view)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.CameraFragment)
        }

        observe()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR;

    }

    private fun observe() = with(viewModel) {
        firstCondition.observe(viewLifecycleOwner) { firstCondition ->
            binding.buttonFirst.isVisible = firstCondition
        }
        orientationLiveData.observe(viewLifecycleOwner) {
            binding.orientationText.text = "device orientation is :$it"
        }
    }

}