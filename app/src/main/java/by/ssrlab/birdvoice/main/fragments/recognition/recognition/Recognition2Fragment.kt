package by.ssrlab.birdvoice.main.fragments.recognition.recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentRecognition2Binding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.rv.Recognition2Adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class  Recognition2Fragment: BaseMainFragment() {

    private lateinit var binding: FragmentRecognition2Binding
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    override var arrayOfViews = arrayListOf<ViewObject>()

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
            layoutManager = LinearLayoutManager(activityMain.getApp().getContext())
            adapter = Recognition2Adapter(
                activityMain.getApp().getContext(),
                mainVM,
                activityMain,
                scope
            )
        }

        mainVM.setToolbarTitle(resources.getString(R.string.recognition_results))
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction { mainVM.recognition2Value.value = true }
        }
    }
}