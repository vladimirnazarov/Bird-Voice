package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentUserDataBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class UserDataFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentUserDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserDataBinding.inflate(layoutInflater)

        animVM.dataDefineElementsVisibility(binding)
        animVM.dataObjectEnter(MainApp.appContext, binding)

        binding.userDataPhotoButton.animation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.userDataApproveButton.isClickable = true
                launchVM.activityBinding?.launcherArrowBack?.isClickable = true

                activityLaunch.setArrowAction {
                    animVM.dataObjectOut(MainApp.appContext, binding)
                    launchVM.navigateUpWithDelay()
                }
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.dataObjectOut(MainApp.appContext, binding) }
    }
}