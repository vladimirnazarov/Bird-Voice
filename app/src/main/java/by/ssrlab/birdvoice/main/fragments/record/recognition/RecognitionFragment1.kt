package by.ssrlab.birdvoice.main.fragments.record.recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRecognition1Binding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecognitionFragment1: BaseMainFragment() {

    private lateinit var binding: FragmentRecognition1Binding
    private var breakableMarker = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecognition1Binding.inflate(layoutInflater)

        animVM.recognition1DefineElementsVisibility(binding)
        animVM.recognition1ObjectEnter(MainApp.appContext, binding)

        activityMain.setPopBackCallback {
            animVM.recognition1ObjectOut(MainApp.appContext, binding)
            breakableMarker = true
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.getScope().launch { toolbarTitleAnimation() }
        mainVM.setToolbarTitle("Recognition service")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction({ animVM.recognition1ObjectOut(MainApp.appContext, binding) }){
                breakableMarker = true
            }
        }
    }

    private suspend fun toolbarTitleAnimation(){
        val dotArray = arrayListOf("", ".", "..", "...")
        var i = 0

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