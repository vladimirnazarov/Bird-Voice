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
import by.ssrlab.birdvoice.helpers.recorder.AudioRecorder
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import java.io.File

class RecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentRecordBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    private val recorder: AudioRecorder by viewModels()
    private var pressedBool = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(recBird),
                ViewObject(recTopRightCloud, "rc1"),
                ViewObject(recBottomLeftCloud, "lc2"),
                ViewObject(recBottomRightCloud, "rc2"),
                ViewObject(recRecordButtonIcon),
                ViewObject(recRecordButtonContainer)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        binding.recBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            binding.recRecordButtonIcon.setOnClickListener { buttonAction() }
        })

        activityMain.deletePopBackCallback()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Record your environment")
        activityMain.setToolbarAction(R.drawable.ic_menu){ activityMain.openDrawer() }
    }

    private fun startRecord(){
        binding.recRecordButtonIcon.setImageResource(
            if (pressedBool) R.drawable.ic_rec_stop
            else R.drawable.ic_rec_start
        )

        if (!pressedBool){
            recorder.stop()

            binding.recRecordButtonIcon.isClickable = false
            animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
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
        else requestRecordPermission { startRecord() }
    }
}