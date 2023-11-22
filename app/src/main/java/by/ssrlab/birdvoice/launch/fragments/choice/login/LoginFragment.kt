package by.ssrlab.birdvoice.launch.fragments.choice.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.client.loginization.LoginClient
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
                ViewObject(loginUsernameTitle),
                ViewObject(loginUsernameInput),
                ViewObject(loginPasswordTitle),
                ViewObject(loginPasswordInput),
                ViewObject(loginShowPasswordButton),
                ViewObject(loginRememberMe),
                ViewObject(loginSignInText),
                ViewObject(loginSignInButton)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews, true)

        binding.loginUsernameInput.filters = helpFunctions.editTextFilters
        binding.loginPasswordInput.filters = helpFunctions.editTextFilters

        if (launchVM.boolPopBack) launchVM.showTop()
        binding.loginBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                    launchVM.hideTop()
                    errorViewOut(checkLogin = true, checkPassword = true)
                }
            }

            binding.loginSignInButton.setOnClickListener {
                checkLogin {
                    LoginClient.post(binding.loginUsernameInput.text!!, binding.loginPasswordInput.text!!, {
                        if (binding.loginRememberMe.isChecked) activityLaunch.getLoginManager().saveToken(it)
                        activityLaunch.runOnUiThread { activityLaunch.moveToMainActivity(recognitionToken = it) }
                    }, { helpFunctions.checkLoginInput(binding.loginUsernameInput, binding.loginUsernameErrorMessage, it, activityLaunch, binding) })
                }
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

        activityLaunch.setPopBackCallback {
            animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
            errorViewOut(checkLogin = true, checkPassword = true)
        }
    }

    private fun checkLogin(onSuccess: () -> Unit){
        var errorValue = 0

        setEditTextListeners()

        errorValue += helpFunctions.checkLoginInput(binding.loginUsernameInput, binding.loginUsernameErrorMessage, activity = activityLaunch, binding = binding)
        errorValue += helpFunctions.checkPasswordInput(binding.loginPasswordInput.text, binding.loginPasswordErrorMessage, resources)

        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(checkLogin: Boolean = false, checkPassword: Boolean = false){
        if (checkLogin) helpFunctions.checkErrorViewAvailability(binding.loginUsernameErrorMessage)
        if (checkPassword) helpFunctions.checkErrorViewAvailability(binding.loginPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.loginUsernameInput.addTextChangedListener(helpFunctions.createEditTextListener ({
            errorViewOut(checkLogin = true)
            binding.loginUsernameInput.setTextColor(ContextCompat.getColor(activityLaunch, R.color.primary_blue)) }, {}))
        binding.loginPasswordInput.addTextChangedListener(helpFunctions.createEditTextListener ({
            errorViewOut(checkPassword = true)
            binding.loginUsernameInput.setTextColor(ContextCompat.getColor(activityLaunch, R.color.primary_blue)) }, {}))
    }
}