package by.ssrlab.birdvoice.launch.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.loginEmailInput.filters = fragmentVM.editTextFilters
        binding.loginPasswordInput.filters = fragmentVM.editTextFilters

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        binding.loginBird.animation.setAnimationListener(fragmentVM.createAnimationEndListener {
            launchVM.setArrowAction {
                animVM.loginObjectOut(MainApp.appContext, binding)
                launchVM.hideArrow()
                launchVM.navigateUpWithDelay()
                errorViewOut(checkLogin = true, checkPassword = true)
            }

            binding.loginSignInButton.setOnClickListener {
                checkLogin()
            }
        })

        fragmentVM.controlPopBack(launchVM, true)

        binding.loginShowPasswordButton.setOnClickListener {
            fragmentVM.setPasswordShowButtonAction(binding.loginPasswordInput, binding.loginShowPasswordButton)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.loginObjectOut(MainApp.appContext, binding) }
    }

    private fun checkLogin(){
        setEditTextListeners()

        fragmentVM.checkTextInput(binding.loginEmailInput.text, binding.loginEmailErrorMessage, resources)
        fragmentVM.checkTextInput(binding.loginPasswordInput.text, binding.loginPasswordErrorMessage, resources)
    }

    private fun errorViewOut(checkLogin: Boolean = false, checkPassword: Boolean = false){
        if (checkLogin) fragmentVM.checkErrorViewAvailability(binding.loginEmailErrorMessage)
        if (checkPassword) fragmentVM.checkErrorViewAvailability(binding.loginPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.loginEmailInput.addTextChangedListener(fragmentVM.createEditTextListener ({ errorViewOut(checkLogin = true) }, {}))
        binding.loginPasswordInput.addTextChangedListener(fragmentVM.createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}