package com.shubhaminflow.imagesearchapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.vaccineslotfinder.CowinRepository
import com.example.vaccineslotfinder.CowinResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HomeViewModel @ViewModelInject constructor(
    public val repository: CowinRepository
) : ViewModel() {

        fun getworkrequest(pincode: String): PeriodicWorkRequest {

            val inputdata = Data.Builder().putString("PINCODE", pincode)

            val workRequest: PeriodicWorkRequest =
                PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
                    .setInputData(inputdata.build())
                    .addTag("checker")
                    .build()

            return workRequest
        }


    fun searchSlots(pincode: String, date: String): Call<CowinResponse> {

        return repository.getSearchResults(pincode, date)

    }

    fun runPeriodicRequest(context: Context, pincode: String) {

        val workRequest1=getworkrequest(pincode)
        WorkManager.getInstance(context).enqueue(workRequest1)
    }




}

