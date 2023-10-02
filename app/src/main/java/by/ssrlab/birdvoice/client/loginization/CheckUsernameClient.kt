package by.ssrlab.birdvoice.client.loginization

import android.text.Editable
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object CheckUsernameClient {

    private var checkUsernameClient: OkHttpClient? = null

    fun post(userName: Editable, password: Editable, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

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
                onFailure(e.message!!)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    val jsonObject = responseBody?.let { it1 -> JSONObject(it1) }
                    val message = jsonObject?.getString("message")

                    if (message == "OK") onSuccess()
                    else message?.let { it1 -> onFailure(it1) }
                }
            }
        })
    }
}