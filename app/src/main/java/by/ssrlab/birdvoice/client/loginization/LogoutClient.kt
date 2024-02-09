package by.ssrlab.birdvoice.client.loginization

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LogoutClient {

    private var logoutClient: OkHttpClient? = null

    fun logOut(refreshToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        if (logoutClient == null) logoutClient = OkHttpClient.Builder().build()

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("refresh", refreshToken)
            .build()

        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/logout/")
            .post(body)
            .build()

        logoutClient?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    val jObject = responseBody?.let { it1 -> JSONObject(it1) }
                    if (jObject?.getString("message") == "Logout success") onSuccess()
                    else jObject?.getString("message")?.let { it1 -> onFailure(it1) }
                }
            }
        })
    }

    fun deleteUser(accountId: Int, refreshToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        if (logoutClient == null) logoutClient = OkHttpClient.Builder().build()

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("id", accountId.toString())
            .addFormDataPart("refresh", refreshToken)
            .build()

        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/delete-user/")
            .post(body)
            .build()

        logoutClient?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    val jObject = responseBody?.let { it1 -> JSONObject(it1) }
                    if (jObject?.getString("message") == "Account deleted") onSuccess()
                    else jObject?.getString("message")?.let { it1 -> onFailure(it1) }
                }
            }
        })
    }
}