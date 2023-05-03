package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRegisterBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class RegisterFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.registerPrivacyPolicy.movementMethod = LinkMovementMethod.getInstance()

        animVM.registerDefineElementsVisibility(binding)
        animVM.registerObjectEnter(MainApp.appContext, binding)

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        binding.registerBird.animation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.registerCreateButton.setOnClickListener {
                    animVM.registerObjectOut(MainApp.appContext, binding)
                    launchVM.navigateToWithDelay(R.id.action_registerFragment_to_codeFragment)
                    binding.registerCreateButton.isClickable = false
                    launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                }

                activityLaunch.setArrowAction {
                    animVM.registerObjectOut(MainApp.appContext, binding)
                    launchVM.hideArrow()
                    launchVM.navigateUpWithDelay()
                }
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        launchVM.boolPopBack = true
        launchVM.boolArrowHide = true

        binding.registerShowPasswordButton.setOnClickListener {
            if (binding.registerPasswordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.registerPasswordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.registerShowPasswordButton.setImageResource(R.drawable.ic_launcher_show_password)
            } else {
                binding.registerPasswordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.registerShowPasswordButton.setImageResource(R.drawable.ic_launcher_hide_password)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.registerObjectOut(MainApp.appContext, binding) }
    }
}