package by.ssrlab.birdvoice.launch.fragments.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class LoginFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        animVM.loginDefineElementsVisibility(binding)
        animVM.loginObjectEnter(MainApp.appContext, binding)

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        activityLaunch.setArrowAction {
            animVM.loginObjectOut(MainApp.appContext, binding)
            launchVM.hideArrow()
            launchVM.navigateUpWithDelay()
        }

        launchVM.boolPopBack = true
        launchVM.boolArrowHide = true

        binding.loginShowPasswordButton.setOnClickListener {
            if (binding.loginPasswordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.loginPasswordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.loginShowPasswordButton.setImageResource(R.drawable.ic_launcher_show_password)
            } else {
                binding.loginPasswordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.loginShowPasswordButton.setImageResource(R.drawable.ic_launcher_hide_password)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.loginObjectOut(MainApp.appContext, binding) }
    }
}