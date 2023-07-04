package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRegisterBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class RegisterFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentRegisterBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(registerBird),
                ViewObject(registerBottomLeftCloud, "lc2"),
                ViewObject(registerTopRightCloud, "rc1"),
                ViewObject(registerBottomRightCloud, "rc2"),
                ViewObject(registerNewAccountText),
                ViewObject(registerSignUpText),
                ViewObject(registerEmailTitle),
                ViewObject(registerEmailInput),
                ViewObject(registerTelephoneTitle),
                ViewObject(registerTelephoneInput),
                ViewObject(registerPasswordTitle),
                ViewObject(registerPasswordInput),
                ViewObject(registerShowPasswordButton),
                ViewObject(registerPrivacyPolicy),
                ViewObject(registerCreateButton)
            )

            registerPrivacyPolicy.movementMethod = LinkMovementMethod.getInstance()

            registerEmailInput.filters = helpFunctions.editTextFilters
            registerTelephoneInput.filters = helpFunctions.editTextFilters
            registerPasswordInput.filters = helpFunctions.editTextFilters
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        if (launchVM.boolPopBack) {
            launchVM.showTop()
        }
        binding.registerBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                    launchVM.hideTop()
                    errorViewOut(checkEmail = true, checkTelephone = true, checkPassword = true)
                }
            }

            binding.registerCreateButton.setOnClickListener {
                checkRegister {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                    launchVM.navigateToWithDelay(R.id.action_registerFragment_to_codeFragment)
                    binding.registerCreateButton.isClickable = false
                    launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                }
            }
        })

        helpFunctions.controlPopBack(launchVM, true)

        binding.registerShowPasswordButton.setOnClickListener { helpFunctions.setPasswordShowButtonAction(binding.registerPasswordInput, binding.registerShowPasswordButton) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback {
            animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
            errorViewOut(checkEmail = true, checkTelephone = true, checkPassword = true)
        }
    }

    private fun checkRegister(onSuccess: () -> Unit){
        var errorValue = 0

        setEditTextListeners()

        errorValue += helpFunctions.checkTextInput(binding.registerEmailInput.text, binding.registerEmailErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.registerTelephoneInput.text, binding.registerTelephoneErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.registerPasswordInput.text, binding.registerPasswordErrorMessage, resources)

        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(checkEmail: Boolean = false, checkTelephone: Boolean = false, checkPassword: Boolean = false){
        if (checkEmail) helpFunctions.checkErrorViewAvailability(binding.registerEmailErrorMessage)
        if (checkTelephone) helpFunctions.checkErrorViewAvailability(binding.registerTelephoneErrorMessage)
        if (checkPassword) helpFunctions.checkErrorViewAvailability(binding.registerPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.registerEmailInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkEmail = true) }, {}))
        binding.registerTelephoneInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkTelephone = true) }, {}))
        binding.registerPasswordInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}