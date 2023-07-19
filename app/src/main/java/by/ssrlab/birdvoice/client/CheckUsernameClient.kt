package by.ssrlab.birdvoice.client

import android.content.Context
import android.text.Editable
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object CheckUsernameClient {

    private var checkUsernameClient: OkHttpClient? = null

    fun post(userName: Editable, password: Editable, onSuccess: () -> Unit, onFailure: () -> Unit, context: Context) {

        if (checkUsernameClient == null) checkUsernameClient = OkHttpClient.Builder().build()

        val mediaType = "application/json".toMediaType()
        val body =
            "{\"username\":\"$userName\",\"password\":\"$password\"}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/check-user/")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()

        checkUsernameClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val jsonObject = response.message
                    if (jsonObject == "OK") onSuccess()
                    else onFailure()
                }
            }
        })
    }
}