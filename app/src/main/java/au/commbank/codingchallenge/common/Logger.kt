package au.commbank.codingchallenge.common

import android.util.Log
import au.commbank.codingchallenge.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Logger @Inject constructor() {
    private val isLogEnabled = BuildConfig.DEBUG

    fun debug(tag: String, message: String) {
        if (isLogEnabled)
            Log.d(tag, message)
    }

    fun error(tag: String, message: String) {
        if (isLogEnabled)
            Log.e(tag, message)
    }
}