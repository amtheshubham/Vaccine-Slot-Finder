package com.shubhaminflow.imagesearchapp

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.vaccineslotfinder.CowinRepository
import javax.inject.Inject


class MyWorkerFactory @Inject constructor (private val repository: CowinRepository) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        // This only handles a single Worker, please don’t do this!!
        // See below for a better way using DelegatingWorkerFactory
        return MyWorker(appContext, workerParameters, repository)

    }
}