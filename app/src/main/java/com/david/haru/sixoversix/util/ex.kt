package com.david.haru.sixoversix.util

import androidx.navigation.NavController


val NavController.currentDestinationId: Int
    get() {
        return currentDestination?.id ?: 0
    }

val Any.CLASS_TAG: String
    get() = this.javaClass.simpleName
