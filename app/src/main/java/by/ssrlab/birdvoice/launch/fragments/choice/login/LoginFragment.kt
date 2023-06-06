package by.ssrlab.birdvoice.launch.fragments.choice.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class LoginFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentLoginBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(loginWelcomeText),
                ViewObject(loginBottomLeftCloud, "lc1"),
                ViewObject(loginBottomRightCloud, "rc2"),
                ViewObject(loginTopRightCloud, "rc1"),
                ViewObject(loginBird),
                ViewObject(loginEmailTitle),
                ViewObject(loginEmailInput),
                ViewObject(loginPasswordTitle),
                ViewObject(loginPasswordInput),
                ViewObject(loginShowPasswordButton),
                ViewObject(loginForgotPassword),
                ViewObject(loginRememberMe),
                ViewObject(loginSignInText),
                ViewObject(loginSignInButton)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        binding.loginEmailInput.filters = helpFunctions.editTextFilters
        binding.loginPasswordInput.filters = helpFunctions.editTextFilters

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        binding.loginBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                    launchVM.hideArrow()
                    errorViewOut(checkLogin = true, checkPassword = true)
                }
            }

            binding.loginSignInButton.setOnClickListener {
                checkLogin()
            }
        })

        helpFunctions.controlPopBack(launchVM, true)

        binding.loginShowPasswordButton.setOnClickListener {
            helpFunctions.setPasswordShowButtonAction(binding.loginPasswordInput, binding.loginShowPasswordButton)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews) }
    }

    private fun checkLogin(){
        setEditTextListeners()

        helpFunctions.checkTextInput(binding.loginEmailInput.text, binding.loginEmailErrorMessage, resources)
        helpFunctions.checkTextInput(binding.loginPasswordInput.text, binding.loginPasswordErrorMessage, resources)
    }

    private fun errorViewOut(checkLogin: Boolean = false, checkPassword: Boolean = false){
        if (checkLogin) helpFunctions.checkErrorViewAvailability(binding.loginEmailErrorMessage)
        if (checkPassword) helpFunctions.checkErrorViewAvailability(binding.loginPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.loginEmailInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkLogin = true) }, {}))
        binding.loginPasswordInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}