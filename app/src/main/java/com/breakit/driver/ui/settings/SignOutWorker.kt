package com.breakit.driver.ui.settings

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.breakit.driver.data.login.LoginRepository
import com.breakit.driver.utils.extensions.injectWorker
import javax.inject.Inject

/**
 * Worker for stopping downloads
 */
class SignOutWorker(
    private val appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {

    /**
     * Settings repository
     */
    @Inject
    lateinit var loginRepository: LoginRepository

    init {
        appContext.injectWorker(this)
    }

    /**
     * See [Worker.doWork]
     */
    override suspend fun doWork(): Result {
        // Clear tables and preferences
        loginRepository.deleteSession()
        return Result.success()
    }
}
