package com.david.haru.sixoversix.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.OrientationEventListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.david.haru.sixoversix.util.CLASS_TAG

class MainViewModel : ViewModel() {

    companion object {
        const val MAX_ORIENTATION = 110
        const val MIN_ORIENTATION = 70
    }

    private var orientationData = 0
    private var isBusy: Boolean = false
    private val handler = Handler(Looper.getMainLooper())


    private val _orientationLiveData = MutableLiveData(0)
    val orientationLiveData: LiveData<Int> = _orientationLiveData

    private val _firstCondition = MutableLiveData(false)
    val firstCondition: MutableLiveData<Boolean> = _firstCondition

    private var previousCondition = false


    private fun orientationInRange() = orientationData in MIN_ORIENTATION..MAX_ORIENTATION


    fun initOrientationEventListener(context: Context) {
        val orientationEventListener = object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                orientationData = orientation
                _orientationLiveData.postValue(orientation)

                if (!orientationInRange()) {
                    Log.i(
                        CLASS_TAG,
                        "orientationEventListener - stop , orientation = $orientationData"
                    )

                    handler.removeCallbacksAndMessages(null)
                    isBusy = false

                    if (previousCondition) {
                        previousCondition = false
                        _firstCondition.postValue(false)
                    }

                }

                if (orientationInRange() && !isBusy && !previousCondition) {
                    Log.i(
                        CLASS_TAG,
                        "orientationEventListener - start , orientation = $orientationData"
                    )
                    isBusy = true
                    handler.postDelayed(
                        {
                            isBusy = false
                            previousCondition = true
                            _firstCondition.postValue(true)
                            Log.i(
                                CLASS_TAG,
                                "orientationEventListener - GO!!!  , orientation = $orientationData"
                            )
                        }, 3000
                    )
                }
            }

        }


        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable()
        }

    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }

}