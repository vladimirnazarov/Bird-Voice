package by.ssrlab.birdvoice.client

import android.content.Context
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object RegistrationClient {

    private var registrationClient: OkHttpClient? = null

    fun build(userName: String, password: String, firstName: String, lastName: String, email: String, onSuccess: (body: String) -> Unit, context: Context) {
        if (registrationClient == null) {

            registrationClient = OkHttpClient.Builder().build()

            val mediaType = "application/json".toMediaType()
            val body = "{\"username\":\"$userName\",\"password\":\"$password\",\"first_name\":\"$firstName\",\"last_name\":\"$lastName\",\"email\":\"$email\"}".toRequestBody(mediaType)
            val request = Request.Builder()
                .url("https://bird-sounds-database.ssrlab.by/api/user-create/")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "csrftoken=sNRO57zm7Pe1MsVxvpULSZzy0qr99141Z4gbpL3yriyWYTTUmSqRqtZI03WscJsS; sessionid=dm09y6nyilr0lsvox3umkb7roy5zwus1")
                .build()

            registrationClient!!.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        val jsonObject = response.body!!.string()
                        onSuccess(jsonObject)
                    }
                }
            })
        }
    }
}