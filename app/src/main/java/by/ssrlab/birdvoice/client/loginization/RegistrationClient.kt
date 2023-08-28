package by.ssrlab.birdvoice.client.loginization

import android.content.Context
import android.text.Editable
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object RegistrationClient {

    private var registrationClient: OkHttpClient? = null

    fun post(userName: Editable, password: Editable, firstName: Editable, lastName: Editable, email: Editable, onSuccess: () -> Unit, onFailure: () -> Unit, context: Context) {

        if (registrationClient == null) registrationClient = OkHttpClient.Builder().build()

        val mediaType = "application/json".toMediaType()
        val body = "{\"username\":\"$userName\",\"password\":\"$password\",\"first_name\":\"$firstName\",\"last_name\":\"$lastName\",\"email\":\"$email\"}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/user-create/")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()

        registrationClient!!.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val jsonObject = response.message
                    if (jsonObject == "Created") onSuccess()
                    else onFailure()
                }
            }
        })
    }
}