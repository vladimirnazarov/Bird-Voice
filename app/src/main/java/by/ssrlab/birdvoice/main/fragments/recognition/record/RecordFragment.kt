package by.ssrlab.birdvoice.main.fragments.recognition.record

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRecordBinding
import by.ssrlab.birdvoice.helpers.createAnimationEndListener
import by.ssrlab.birdvoice.helpers.recorder.AudioRecorder
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import java.io.File

class RecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentRecordBinding
    private val recorder: AudioRecorder by viewModels()
    private var pressedBool = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordBinding.inflate(layoutInflater)

        animVM.recDefineElementsVisibility(binding)
        animVM.recObjectEnter(MainApp.appContext, binding)

        binding.recBird.animation.setAnimationListener(createAnimationEndListener {
            binding.recRecordButtonIcon.setOnClickListener { buttonAction() }
        })

        activityMain.setPopBackCallback {
            animVM.recObjectOut(MainApp.appContext, binding)
            binding.recRecordButtonIcon.setImageResource(R.drawable.ic_rec_start)
            pressedBool = true
            binding.recRecordButtonIcon.isClickable = false
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Record your environment")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction({ animVM.recObjectOut(MainApp.appContext, binding) }, {
                binding.recRecordButtonIcon.setImageResource(R.drawable.ic_rec_start)
                pressedBool = true
                binding.recRecordButtonIcon.isClickable = false
            })
        }
    }

    private fun startRecord(){
        binding.recRecordButtonIcon.setImageResource(
            if (pressedBool) R.drawable.ic_rec_stop
            else R.drawable.ic_rec_start
        )

        if (!pressedBool){
            recorder.stop()

            binding.recRecordButtonIcon.isClickable = false
            animVM.recObjectOut(MainApp.appContext, binding)
            mainVM.navigateToWithDelay(R.id.action_recordFragment_to_editRecordFragment)
        } else {
            File(activityMain.cacheDir, "audio.mp3").also {
                recorder.start(it)
                mainVM.tempAudioFile = it
            }
        }

        pressedBool = !pressedBool
    }

    private fun requestRecordPermission(onSuccess: () -> Unit){
        while (ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activityMain,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        }
        onSuccess()
    }

    private fun buttonAction(){
        if (ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) startRecord()
        else requestRecordPermission({ startRecord() })
    }
}