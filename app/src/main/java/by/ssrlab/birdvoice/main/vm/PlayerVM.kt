package by.ssrlab.birdvoice.main.vm

import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentEditRecordBinding
import by.ssrlab.birdvoice.helpers.convertToTimerMode
import by.ssrlab.birdvoice.helpers.createSeekBarProgressListener
import com.airbnb.lottie.LottieDrawable
import kotlinx.coroutines.*

class PlayerVM: ViewModel() {

    private var mpStatus = "play"
    private var viewModelPlayerStatus = 0

    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
    private var mediaPlayer: MediaPlayer? = null

    private var binding: FragmentEditRecordBinding? = null

    fun initializeMediaPlayer(uri: Uri){

        if (viewModelPlayerStatus == 0) {

            mpStatus = "play"

            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setDataSource(MainApp.appContext, uri)
            mediaPlayer!!.prepare()

            binding!!.apply {
                editRecAudioProgress.max = mediaPlayer!!.duration
                editRecAudioProgress.progress = 0
                editRecPlayButton.setImageResource(R.drawable.ic_play_button)
            }

            listenProgress(mediaPlayer!!)

            viewModelPlayerStatus = 1
        }
    }

    fun playAudio(){
        mediaScope.launch {
            when (mpStatus) {

                "pause" -> {
                    mediaPlayer!!.pause()
                    binding!!.apply {
                        editRecPlayButton.setImageResource(R.drawable.ic_play_button)
                        editRecWaveAnimation.pauseAnimation()
                    }
                    mpStatus = "continue"
                }
                "continue" -> {
                    mediaPlayer!!.start()
                    binding!!.apply {
                        editRecPlayButton.setImageResource(R.drawable.ic_pause_button)
                        editRecWaveAnimation.playAnimation()
                    }
                    mpStatus = "pause"
                    mediaScope.launch { initProgressListener(mediaPlayer!!) }
                }
                "play" -> {
                    try {
                        mediaPlayer!!.start()

                        mediaScope.launch { initProgressListener(mediaPlayer!!) }

                        binding!!.apply {
                            editRecPlayButton.setImageResource(R.drawable.ic_pause_button)
                            editRecWaveAnimation.apply {
                                playAnimation()
                                repeatMode = LottieDrawable.REVERSE
                                repeatCount = LottieDrawable.INFINITE
                            }
                        }

                        mpStatus = "pause"

                    } catch (e: Exception){
                        Toast.makeText(MainApp.appContext, "Player error: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun mpStop(){
        mpStatus = "stop"

        if (mediaPlayer?.isPlaying == true){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        } else mediaPlayer?.release()

        binding!!.editRecWaveAnimation.apply {
            pauseAnimation()
            cancelAnimation()
        }
    }

    private suspend fun initProgressListener(mediaPlayer: MediaPlayer){
        while (mpStatus == "pause") {
            binding!!.apply {
                editRecTimer.text = convertToTimerMode(mediaPlayer.currentPosition)
                editRecAudioProgress.progress = mediaPlayer.currentPosition
            }
            delay(250)

            binding!!.editRecAudioProgress.apply {
                if (progress == max) {
                    mpStatus = "play"
                    delay(250)

                    mediaPlayer.seekTo(0)
                    binding!!.apply {
                        editRecPlayButton.setImageResource(R.drawable.ic_play_button)
                        editRecAudioProgress.progress = 0
                        editRecTimer.text = convertToTimerMode(mediaPlayer.currentPosition)
                        editRecWaveAnimation.apply {
                            pauseAnimation()
                            cancelAnimation()
                        }
                    }
                }
            }
        }
    }

    private fun listenProgress(mediaPlayer: MediaPlayer){
        binding!!.editRecAudioProgress.setOnSeekBarChangeListener(createSeekBarProgressListener {
            mediaPlayer.seekTo(it)
            binding!!.editRecTimer.text = convertToTimerMode(mediaPlayer.currentPosition)
        })
    }

    fun saveBinding(b: FragmentEditRecordBinding){
        binding = b
    }
    fun getBinding() = binding!!
    fun checkIfBindingSaved(): Boolean = binding == null

    override fun onCleared() {
        super.onCleared()

        mpStop()
    }
}