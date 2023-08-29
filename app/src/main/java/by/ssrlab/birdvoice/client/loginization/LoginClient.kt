package by.ssrlab.birdvoice.client.loginization

import android.content.Context
import android.text.Editable
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object LoginClient {

    private var loginClient: OkHttpClient? = null

    fun post(username: Editable, password: Editable, onSuccess: (String) -> Unit, onFailure: () -> Unit, context: Context) {

        if (loginClient == null) loginClient = OkHttpClient.Builder().build()

        val mediaType = "application/json".toMediaType()
        val body = "{\"username\":\"$username\",\"password\":\"$password\"}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/login-api/")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()

        loginClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    val jObject = responseBody?.let { it1 -> JSONObject(it1) }
                    if (jObject?.getString("message") == "Login successfull") onSuccess(jObject.getJSONObject("token").getString("access"))
                    else onFailure()
                }
            }
        })
    }
}