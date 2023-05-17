package by.ssrlab.birdvoice.main.fragments.record.recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRecognition1Binding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment

class RecognitionFragment1: BaseMainFragment() {

    private lateinit var binding: FragmentRecognition1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecognition1Binding.inflate(layoutInflater)

        animVM.recognition1DefineElementsVisibility(binding)
        animVM.recognition1ObjectEnter(MainApp.appContext, binding)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Recognition Service")
    }
}