package com.david.haru.sixoversix.camera

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.david.haru.sixoversix.util.CLASS_TAG
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceAnalysis(private val IFaceAnalysisCallBack: IFaceAnalysisCallBack) :
    ImageAnalysis.Analyzer {

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)


    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        mediaImage?.let { image ->
            detectInImage(InputImage.fromMediaImage(image, 180))
                .addOnSuccessListener { results ->
                    onSuccess(
                        results
                    )
                    imageProxy.close()
                }
                .addOnFailureListener { ex ->
                    onFailure(ex)
                    imageProxy.close()
                }
        }
    }

    private fun detectInImage(image: InputImage): Task<List<Face>> {
        return detector.process(image)
    }

    private fun onSuccess(results: List<Face>) {
        IFaceAnalysisCallBack.isFaceDetected(results.isNotEmpty())
    }

    private fun onFailure(e: Exception) {
        IFaceAnalysisCallBack.isFaceDetected(false)
        Log.w(CLASS_TAG, "Face detected failed", e)
    }

}