package com.example.networkdemoapplication.util

import android.location.Location

/**
 * Created by Ritik on 7/18/2023.
 */
interface MainActivityListener {
    fun getLocation(listener: (Location?) -> Unit)
}