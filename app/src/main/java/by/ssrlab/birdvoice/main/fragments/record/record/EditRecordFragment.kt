package by.ssrlab.birdvoice.main.fragments.record.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentEditRecordBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import com.airbnb.lottie.LottieDrawable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditRecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentEditRecordBinding
    private var isPlaying = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditRecordBinding.inflate(layoutInflater)

        animVM.editRecDefineElementsVisibility(binding)
        animVM.editRecordObjectEnter(MainApp.appContext, binding)

        binding.editRecPlayButton.setOnClickListener {
            binding.editRecWaveAnimation.apply {
                if (isPlaying){
                    cancelAnimation()
                    progress /= 2
                    mainVM.getScope().launch {
                        while (progress != 0f) {
                            delay(10)
                            progress -= 0.01f
                        }
                    }
                    isPlaying = !isPlaying
                } else {
                    playAnimation()
                    repeatMode = LottieDrawable.REVERSE
                    repeatCount = LottieDrawable.INFINITE
                    isPlaying = !isPlaying
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Listen to your record")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction({ animVM.editRecordObjectOut(MainApp.appContext, binding) }, {})
        }
    }
}