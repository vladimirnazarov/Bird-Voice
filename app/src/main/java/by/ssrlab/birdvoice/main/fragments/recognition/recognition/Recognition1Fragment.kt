package by.ssrlab.birdvoice.main.fragments.recognition.recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.client.RecognitionClient
import by.ssrlab.birdvoice.databinding.FragmentRecognition1Binding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Recognition1Fragment: BaseMainFragment() {

    private lateinit var binding: FragmentRecognition1Binding
    override lateinit var arrayOfViews: ArrayList<ViewObject>
    private var breakableMarker = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecognition1Binding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(recognitionBird),
                ViewObject(recognitionText),
                ViewObject(recognitionPlatform),
                ViewObject(recognitionBottomLeftCloud, "lc1"),
                ViewObject(recognitionTopLeftCloud, "lc2"),
                ViewObject(recognitionTopRightCloud, "rc1")
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        activityMain.setPopBackCallback {
            animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
            breakableMarker = true
            binding.recognitionDots.visibility = View.INVISIBLE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.getScope().launch { toolbarTitleAnimation() }
        mainVM.setToolbarTitle("Recognition service")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction {
                animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                breakableMarker = true
                binding.recognitionDots.visibility = View.INVISIBLE
            }
        }

        mainVM.getAudioFile()?.let { RecognitionClient.post(mainVM.getToken(), it, 0, { list ->
            mainVM.setList(list)
        }) { string ->
            activityMain.runOnUiThread { Toast.makeText(activityMain, string, Toast.LENGTH_SHORT).show() }
        }}

        mainVM.recognition2Value.value = false

        mainVM.getScope().launch {
            delay(5000)
            if (!breakableMarker) {
                animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                mainVM.navigateToWithDelay(R.id.action_recognitionFragment1_to_recognitionFragment2)
                binding.recognitionDots.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        breakableMarker = true
    }

    private suspend fun toolbarTitleAnimation(){
        val dotArray = arrayListOf("", ".", "..", "...")
        var i = 0

        binding.recognitionDots.visibility = View.VISIBLE

        val a = {
            binding.recognitionDots.text = dotArray[i]
            i++
            if (i == dotArray.size) i = 0
        }

        while (!breakableMarker) {
            a()
            delay(500)
        }
    }
}