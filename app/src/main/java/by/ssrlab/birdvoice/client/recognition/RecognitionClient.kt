package by.ssrlab.birdvoice.client.recognition

import by.ssrlab.birdvoice.db.objects.RecognizedBird
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object RecognitionClient {

    private var recognitionClient = OkHttpClient.Builder()
        .callTimeout(3, TimeUnit.MINUTES)
        .connectTimeout(3, TimeUnit.MINUTES)
        .readTimeout(3, TimeUnit.MINUTES)
        .writeTimeout(3, TimeUnit.MINUTES)
        .build()

    fun sendToDatabase(audioFile: File, token: String, username: String, onSuccess: (ArrayList<RecognizedBird>) -> Unit, onFailure: (String) -> Unit) {

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("audio_to_recognize", "$username android", audioFile.asRequestBody("application/octet-stream".toMediaType()))
            .addFormDataPart("access",token)
            .addFormDataPart("username",username)
            .build()

        val request = Request.Builder()
            .url("https://bird-sounds-database.ssrlab.by/api/recognize/")
            .post(body)
            .addHeader("Cookie", "csrftoken=GnJmqyQOhvOhzOQ39Dczs8EorOUWAUr3A1UyxuZ6LVFOWFWwshjVEmyGbCEEfK0o")
            .build()

        recognitionClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val midString = responseBody?.substring(1, responseBody.length - 1)
                val finalString = midString?.replace("\\\"", "\"")

                try {
                    val jObject = finalString?.let { JSONObject(it) }
                    val keys = jObject?.keys()

                    val arrayOfResults = arrayListOf<RecognizedBird>()
                    while (keys?.hasNext() == true) {
                        val key = keys.next()
                        val birdInfoArray = jObject.getString(key)

                        val recognizedBird = RecognizedBird(
                            image = birdInfoArray,
                            name = key
                        )

                        arrayOfResults.add(recognizedBird)
                    }

                    onSuccess(arrayOfResults)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    onFailure(e.message.toString())
                }
            }
        })
    }
}