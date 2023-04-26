package by.ssrlab.birdvoice.launch.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentLogoBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment
import kotlinx.coroutines.*

class LogoFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentLogoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLogoBinding.inflate(layoutInflater)

        activityLaunch.hideStatusBar()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        animVM.setLaunchVM(launchVM)

        launchVM.getScope().launch {
            delay(3000)
            binding.logoLottie.animate().apply {
                duration = 500
                alpha(0f)

                withEndAction {
                    launchVM.navigate(R.id.action_logoFragment_to_choiceFragment)
                }
            }
        }
    }
}