package by.ssrlab.birdvoice.launch.fragments.choice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentChoiceBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class ChoiceFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChoiceBinding.inflate(layoutInflater)

        activityLaunch.showStatusBar()
        animVM.choiceDefineElementsVisibility(binding)
        animVM.choiceObjectEnter(MainApp.appContext, binding)

        binding.choiceBird.animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.choiceLoginButton.setOnClickListener {
                    animVM.choiceObjectOut(MainApp.appContext, binding)
                    binding.choiceLoginButton.isClickable = false
                    launchVM.navigateToWithDelay(R.id.action_choiceFragment_to_loginFragment)
                }

                binding.choiceRegisterButton.setOnClickListener {
                    animVM.choiceObjectOut(MainApp.appContext, binding)
                    binding.choiceRegisterButton.isClickable = false
                    launchVM.navigateToWithDelay(R.id.action_choiceFragment_to_registerFragment)
                }
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.deletePopBackCallback()
    }
}