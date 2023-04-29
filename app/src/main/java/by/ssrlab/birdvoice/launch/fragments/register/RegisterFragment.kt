package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        activityLaunch.showArrow()
        activityLaunch.setArrowAction {
            animVM.registerObjectOut(MainApp.appContext, binding)
            activityLaunch.hideArrow()
            launchVM.navigateUpWithDelay()
        }

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

        binding.registerCreateButton.setOnClickListener {
            animVM.registerObjectOut(MainApp.appContext, binding)
            launchVM.navigateToWithDelay(R.id.action_registerFragment_to_codeFragment)
        }
    }
}