package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRegisterBinding
import by.ssrlab.birdvoice.helpers.*
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

        binding.registerEmailInput.filters = editTextFilters
        binding.registerTelephoneInput.filters = editTextFilters
        binding.registerPasswordInput.filters = editTextFilters

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        binding.registerBird.animation.setAnimationListener(createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction({ animVM.registerObjectOut(MainApp.appContext, binding) }){
                    launchVM.hideArrow()
                    errorViewOut(checkEmail = true, checkTelephone = true, checkPassword = true)
                }
            }

            binding.registerCreateButton.setOnClickListener {
                checkRegister {
                    animVM.registerObjectOut(MainApp.appContext, binding)
                    launchVM.navigateToWithDelay(R.id.action_registerFragment_to_codeFragment)
                    binding.registerCreateButton.isClickable = false
                    launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                }
            }
        })

        controlPopBack(launchVM, true)

        binding.registerShowPasswordButton.setOnClickListener { setPasswordShowButtonAction(binding.registerPasswordInput, binding.registerShowPasswordButton) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.registerObjectOut(MainApp.appContext, binding) }
    }

    private fun checkRegister(onSuccess: () -> Unit){
        var errorValue = 0

        setEditTextListeners()

        errorValue += checkTextInput(binding.registerEmailInput.text, binding.registerEmailErrorMessage, resources)
        errorValue += checkTextInput(binding.registerTelephoneInput.text, binding.registerTelephoneErrorMessage, resources)
        errorValue += checkTextInput(binding.registerPasswordInput.text, binding.registerPasswordErrorMessage, resources)

        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(checkEmail: Boolean = false, checkTelephone: Boolean = false, checkPassword: Boolean = false){
        if (checkEmail) checkErrorViewAvailability(binding.registerEmailErrorMessage)
        if (checkTelephone) checkErrorViewAvailability(binding.registerTelephoneErrorMessage)
        if (checkPassword) checkErrorViewAvailability(binding.registerPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.registerEmailInput.addTextChangedListener(createEditTextListener ({ errorViewOut(checkEmail = true) }, {}))
        binding.registerTelephoneInput.addTextChangedListener(createEditTextListener ({ errorViewOut(checkTelephone = true) }, {}))
        binding.registerPasswordInput.addTextChangedListener(createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}