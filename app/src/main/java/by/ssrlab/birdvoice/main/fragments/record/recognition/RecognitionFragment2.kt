package by.ssrlab.birdvoice.main.fragments.record.recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRecognition2Binding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.rv.Recognition2Adapter

class RecognitionFragment2: BaseMainFragment() {

    private lateinit var binding: FragmentRecognition2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecognition2Binding.inflate(layoutInflater)

        activityMain.setPopBackCallback{ mainVM.recognition2Value.value = true }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.recognition2Rv.apply {
            layoutManager = LinearLayoutManager(MainApp.appContext)
            adapter = Recognition2Adapter(
                MainApp.appContext,
                mainVM,
                activityMain,
                resources.getString(R.string.general_information),
                resources.getString(R.string.scientific_information)
            )
        }

        mainVM.setToolbarTitle("Recognition results")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction({}){ mainVM.recognition2Value.value = true }
        }
    }
}