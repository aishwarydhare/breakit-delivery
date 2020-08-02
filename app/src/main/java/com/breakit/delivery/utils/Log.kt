package com.breakit.delivery.utils

import android.util.Log
import com.breakit.delivery.BuildConfig

/**
 * Logger
 */
interface Logger {
    /**
     * Log exception
     */
    fun log(e: Throwable)

    /**
     * Log error
     */
    fun log(e: Error)
}

/**
 * LoggerImpl
 */
class LoggerImpl : Logger {
    override fun log(e: Throwable) {
        com.breakit.delivery.utils.Log.exception(e)
    }

    override fun log(e: Error) {
        com.breakit.delivery.utils.Log.error(e)
    }
}

/**
 * Log Utils
 */
internal object Log {

    /**
     * Log debug message
     */
    fun debug(message: String, tag: String? = BuildConfig.APPLICATION_ID.toUpperCase()) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message)
        }
    }

    /**
     * Log Exception
     */
    fun exception(e: Throwable) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        } else {
            //todo: enable crashlytics
//            Crashlytics.logException(e)
        }
    }

    /**
     * Log error
     */
    fun error(e: Error) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        } else {
            //todo: enable crashlytics
//            Crashlytics.logException(e)
        }
    }
}
