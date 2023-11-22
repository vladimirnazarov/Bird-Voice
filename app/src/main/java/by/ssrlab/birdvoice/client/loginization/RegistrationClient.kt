package by.ssrlab.birdvoice.client.loginization

import android.text.Editable
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object RegistrationClient {

    private var registrationClient: OkHttpClient? = null

    fun post(email: Editable, password: Editable, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        if (registrationClient == null) registrationClient = OkHttpClient.Builder().build()

        val username = email.toString().substringBefore("@")

        val mediaType = "application/json".toMediaType()
        val body = "{\"username\":\"$username\",\"password\":\"$password\",\"first_name\":\"$username\",\"last_name\":\"$username\",\"email\":\"$email\"}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/user-create/")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()

        registrationClient!!.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message!!)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    val jObject = responseBody?.let { it1 -> JSONObject(it1) }
                    if (jObject?.getString("message") == "Registration successful") onSuccess()
                    else jObject?.getString("message")?.let { it1 -> onFailure(it1) }
                }
            }
        })
    }
}