package by.ssrlab.birdvoice.launch.fragments.choice.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
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

        if (launchVM.boolPopBack) {
            launchVM.showTop()
        }
        binding.loginBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                    launchVM.hideTop()
                    errorViewOut(checkLogin = true, checkPassword = true)
                }
            }

            binding.loginSignInButton.setOnClickListener {
                checkLogin{
                    LoginClient.post(binding.loginUsernameInput.text.toString(), binding.loginPasswordInput.text.toString(), {
                        if (binding.loginRememberMe.isChecked) activityLaunch.getLoginManager().saveToken(it)
                        activityLaunch.runOnUiThread { activityLaunch.moveToMainActivity(recognitionToken = it) }
                    }, { activityLaunch.runOnUiThread { Toast.makeText(activityLaunch, it, Toast.LENGTH_SHORT).show() } })
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

        errorValue += helpFunctions.checkTextInput(binding.loginUsernameInput.text, binding.loginUsernameErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.loginPasswordInput.text, binding.loginPasswordErrorMessage, resources)

        if (binding.loginUsernameInput.text!!.isNotEmpty()) {
            val emailRegex = Regex("^[a-zA-Z0-9][a-zA-Z0-9]*@[a-zA-Z][a-zA-Z]+\\.[a-zA-Z][a-zA-Z]+")
            val errorAnim = AnimationUtils.loadAnimation(activityLaunch, R.anim.common_error_message_animation)
            if (!emailRegex.matches(binding.loginUsernameInput.text!!)) {
                binding.apply {
                    loginUsernameInput.setTextColor(ContextCompat.getColor(activityLaunch, R.color.primary_red))
                    loginUsernameErrorMessage.text = resources.getText(R.string.email_error_valid)
                    loginUsernameErrorMessage.startAnimation(errorAnim)
                    loginUsernameErrorMessage.visibility = View.VISIBLE
                }
                errorValue += 1
            }
        }

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
        binding.loginPasswordInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}