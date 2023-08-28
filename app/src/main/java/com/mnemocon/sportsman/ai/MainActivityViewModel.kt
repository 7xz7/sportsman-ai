package com.mnemocon.sportsman.ai

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.mnemocon.sportsman.ai.data.Get
import com.mnemocon.sportsman.ai.service.NetworkService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    val _userUUID = MutableLiveData<String>().apply {
        value = "asdf"
    }
    val userUUID: LiveData<String> = _userUUID

    val _userName = MutableLiveData<String>().apply {
        value = "Default Name" // Задай здесь начальное значение, если необходимо
    }
    val userName: LiveData<String> = _userName

    // Метод для обновления имени пользователя
    fun updateUserName(newName: String) {
        _userName.value = newName
        // Отправка нового имени на сервер или другие действия
    }
    fun updateFirebaseUserName(newName: String) {
        val user = FirebaseAuth.getInstance().currentUser

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUserName(newName) // Обновление LiveData с именем пользователя
                }
            }
    }
    //Отправляем данные о Пользователе
    fun sendUserData(userUUID:String, userName:String, userEmail:String){
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("user_id",userUUID)
        jsonObject.put("device_uuid", userUUID)
        jsonObject.put("user_name", userName)
        jsonObject.put("user_email", userEmail)
        val json = jsonObject.toString()
        val body: RequestBody =
            json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        NetworkService.instance?.getJSONApi()?.createUser(body)
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
}