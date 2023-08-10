package com.mnemocon.sportsman.ai

import android.app.Application
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.mnemocon.sportsman.ai.classification.PoseClassifierProcessor
import com.mnemocon.sportsman.ai.data.Dao
import com.mnemocon.sportsman.ai.data.Get
import com.mnemocon.sportsman.ai.service.NetworkService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel (val database: Dao, safeContext: Application) : AndroidViewModel(safeContext) {
    var prothom: Boolean = true
    var isFlash: Boolean = false
    var which_camera: Int = 1
    val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)
    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
    var pushups: Int = 0
    var squats: Int = 0
    var isStart: Boolean = false
    var start_time: Long = 0

    var pushups_cnt: Int = 0
    var squats_cnt: Int = 0
    var now: String = "nothing"

    var mute: Boolean = false

    val poseClassifierProcessor = PoseClassifierProcessor(safeContext, true)

    val options = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
        .build()


    val poseDetector = PoseDetection.getClient(options)

    //Отправляем данные об упражнении
    fun sendExerciseData(user_UUID:String){
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("user_id",user_UUID)
        jsonObject.put("device_uuid", user_UUID)
        jsonObject.put("activity_type", if (squats_cnt > 0) "Squats" else "Pushups")
        jsonObject.put("activity_count", if(squats_cnt > 0) squats_cnt.toString() else pushups_cnt.toString())
        jsonObject.put("time_spent", ((System.currentTimeMillis()/1000) - start_time).toString())
        val json = jsonObject.toString()
        val body: RequestBody =
            json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        NetworkService.instance?.getJSONApi()?.createUserExercise(body)
            ?.enqueue(object : Callback<Get?> {
                override fun onResponse(call: Call<Get?>, response: Response<Get?>) {
                    val post = response.body()
/*                    if( post?.count_users != "0") {
                        userCount.postValue(post?.count_users)
                        isSendExercise.postValue(true)
                    }
                    isLoading.postValue(false)*/
                }

                override fun onFailure(call: Call<Get?>, t: Throwable) {
                   // isLoading.postValue(false)
                }
            })
    }

    class Factory(val database: Dao, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CameraViewModel(database, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

