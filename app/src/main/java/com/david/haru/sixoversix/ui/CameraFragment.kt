package com.david.haru.sixoversix.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.david.haru.sixoversix.R

class CameraFragment : Fragment(R.layout.fragment_camera) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR;

        ViewModelProvider(this)[CameraViewModel::class.java].apply {
            startCamera(
                requireContext(),
                view.findViewById(R.id.previewView_finder),
                viewLifecycleOwner
            ) { isFaceDetected ->
                view.findViewById<View>(R.id.box).isVisible = isFaceDetected
                view.findViewById<View>(R.id.text).isVisible = isFaceDetected
            }
        }
    }
}