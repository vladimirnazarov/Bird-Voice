package by.ssrlab.birdvoice.client.recognition

import by.ssrlab.birdvoice.db.objects.RecognizedBird
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException

object RecognitionClient {

    private var recognitionClient: OkHttpClient? = null

    fun post(token: String, audioFile: File, language: Int, arrayAction: (ArrayList<RecognizedBird>) -> Unit, onFailure: (String) -> Unit) {

        if (recognitionClient == null) recognitionClient = OkHttpClient.Builder().build()

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("audio_file","audio_file", audioFile.asRequestBody("application/octet-stream".toMediaType()))
            .addFormDataPart("language", language.toString())
            .build()
        val request = Request.Builder()
            .url("https://apiptushki.ssrlab.by/predict")
            .post(body)
            .addHeader("Authorization", "Bearer $token")
            .build()

        recognitionClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message!!)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    try {
                        val jObject = responseBody?.let { it1 -> JSONObject(it1) }
                        val predictions = jObject?.getJSONObject("predictions")
                        val keys = predictions?.keys()

                        val arrayOfResults = arrayListOf<RecognizedBird>()

                        while (keys?.hasNext() == true) {
                            val key = keys.next()

                            val birdInfoArray = predictions.getJSONArray(key)
                            val timeArray = birdInfoArray[1] as JSONArray

                            val recognizedBird = RecognizedBird(
                                birdInfoArray[0] as String,
                                key,
                                timeArray.getString(0),
                                timeArray.getString(1)
                            )

                            arrayOfResults.add(recognizedBird)
                        }

                        arrayAction(arrayOfResults)
                    } catch (e: Throwable) {
                        if (responseBody != null) {
                            onFailure(responseBody)
                        }
                    }
                }
            }
        })
    }
}