package com.david.haru.sixoversix.ui

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.david.haru.sixoversix.camera.CameraManager
import com.david.haru.sixoversix.camera.IFaceAnalysisCallBack

class CameraViewModel : ViewModel() {


    fun startCamera(
        context: Context,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        iFaceAnalysisCallBack: IFaceAnalysisCallBack
    ) {
        CameraManager(
            context = context,
            previewView = previewView,
            lifecycleOwner = lifecycleOwner,
            IFaceAnalysisCallBack = iFaceAnalysisCallBack
        ).startCamera()

    }


}