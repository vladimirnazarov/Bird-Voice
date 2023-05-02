package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentCodeConfirmationBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class CodeFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentCodeConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCodeConfirmationBinding.inflate(layoutInflater)

        animVM.codeDefineElementsVisibility(binding)
        animVM.codeObjectEnter(MainApp.appContext, binding)

        binding.codeBird.animation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                activityLaunch.setArrowAction {
                    animVM.codeObjectOut(MainApp.appContext, binding)
                    launchVM.navigateUpWithDelay()
                }
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        launchVM.boolPopBack = false
        launchVM.boolArrowHide = false

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.codeObjectOut(MainApp.appContext, binding) }
    }
}