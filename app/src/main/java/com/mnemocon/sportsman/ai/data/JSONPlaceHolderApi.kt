package com.mnemocon.sportsman.ai.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


class Get {
    @SerializedName("count_users")
    @Expose
    var count_users = ""

    @SerializedName("message")
    @Expose
    var message = ""

}

class Exercise {
    @SerializedName("user_id")
    @Expose
    var user_id: String? = ""

    @SerializedName("device_uuid")
    @Expose
    var device_uuid: String? = ""

    @SerializedName("activity_type")
    @Expose
    var activity_type: String? = ""

    @SerializedName("activity_count")
    @Expose
    var activity_count: Int? = 0

    @SerializedName("time_spent")
    @Expose
    var time_spent: Int? = 0

}

interface JSONPlaceHolderApi {
    @POST("api/aisport/add_exercise.php")
    fun createUserExercise(@Body params: RequestBody): Call<Get?>?

    @GET("api/exercise/getcountusers.php")
    fun getCountUsers(): Call<Get?>?

}
