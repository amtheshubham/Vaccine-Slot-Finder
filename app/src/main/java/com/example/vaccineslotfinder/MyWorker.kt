package com.shubhaminflow.imagesearchapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vaccineslotfinder.CowinRepository
import com.example.vaccineslotfinder.CowinResponse
import com.example.vaccineslotfinder.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MyWorker (appContext: Context, workerParams: WorkerParameters, private var repository: CowinRepository)
    : Worker(appContext, workerParams) {

//    @EntryPoint
//    @InstallIn(ApplicationComponent::class)
//    interface MyWorkerEntryPoint {
//        fun repoService(): UnsplashRepository
//    }
    var notificationManager: NotificationManager= appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    private val context= appContext

    override fun doWork(): Result {

        //(applicationContext as? ImageSearchApplication)?.daggerComponent?.inject(this)
        //var pincode = inputData.getString("pincode")?: "1100086"
        val TAG = "mydateeeeeeeeee"
        val TAG2 = "hehevro"
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val currentDT: String = simpleDateFormat.format(Date())
        val pincode1:String = inputData.getString("PINCODE")?: "110086"
        Log.i(TAG, currentDT)

//        val appContext1 = context?.applicationContext?: throw IllegalStateException()//applicationContext
//        val hiltEntryPoint =
//            EntryPointAccessors.fromApplication(appContext1, MyWorkerEntryPoint::class.java)//fromApplication(appContext, ExampleContentProviderEntryPoint::class.java)
//        val repository = hiltEntryPoint.repoService()

        val call = repository.getSearchResults(pincode1, currentDT)
        call.enqueue(object : Callback<CowinResponse> {
            override fun onResponse(
                call: Call<CowinResponse>,
                response: Response<CowinResponse>
            ) {
                if (!response.isSuccessful()) {
                    //textViewError.setText("Code: " + response.code());
                    //return;
                }
                if (response.code() == 200) {
                    Log.i(TAG2, "huaaaaaaaaaaa")
                    val apiresponse = response.body()!!
                    for (i in apiresponse.resultslist!!) {

                        //
                        Log.i(TAG2, "huaaaaaaaaaaa")
                        if (i.min_age_limit == 18) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationChannel = NotificationChannel(
                                    channelId,
                                    description,
                                    NotificationManager.IMPORTANCE_HIGH
                                )
                                //notificationChannel.enableLights(true)
                                //notificationChannel.lightColor = Color.GREEN
                                notificationChannel.enableVibration(true)
                                notificationManager.createNotificationChannel(notificationChannel)

                                builder = Notification.Builder(applicationContext, channelId)
                                    .setContentText("Vaccine Slots available")
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                                //.setContentIntent(pendingIntent)
                            } else {

                                builder = Notification.Builder(applicationContext)
                                    .setContentText("Vaccine Slots available")
                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                    .setVibrate(longArrayOf(1000,1000))
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                                //.setContentIntent(pendingIntent)
                            }
                            notificationManager.notify(1234, builder.build())

                        }
                    }
                }
            }

            override fun onFailure(call: Call<CowinResponse>, t: Throwable) {
                //
            }

        })

        return Result.success()
    }
}