package by.ssrlab.birdvoice.launch.fragments.choice.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import by.ssrlab.birdvoice.helpers.*
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

        binding.loginEmailInput.filters = editTextFilters
        binding.loginPasswordInput.filters = editTextFilters

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        binding.loginBird.animation.setAnimationListener(createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction({ animVM.loginObjectOut(MainApp.appContext, binding) }){
                    launchVM.hideArrow()
                    errorViewOut(checkLogin = true, checkPassword = true)
                }
            }

            binding.loginSignInButton.setOnClickListener {
                checkLogin()
            }
        })

        controlPopBack(launchVM, true)

        binding.loginShowPasswordButton.setOnClickListener {
            setPasswordShowButtonAction(binding.loginPasswordInput, binding.loginShowPasswordButton)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.loginObjectOut(MainApp.appContext, binding) }
    }

    private fun checkLogin(){
        setEditTextListeners()

        checkTextInput(binding.loginEmailInput.text, binding.loginEmailErrorMessage, resources)
        checkTextInput(binding.loginPasswordInput.text, binding.loginPasswordErrorMessage, resources)
    }

    private fun errorViewOut(checkLogin: Boolean = false, checkPassword: Boolean = false){
        if (checkLogin) checkErrorViewAvailability(binding.loginEmailErrorMessage)
        if (checkPassword) checkErrorViewAvailability(binding.loginPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.loginEmailInput.addTextChangedListener(createEditTextListener ({ errorViewOut(checkLogin = true) }, {}))
        binding.loginPasswordInput.addTextChangedListener(createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}