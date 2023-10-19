package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.client.loginization.CheckUsernameClient
import by.ssrlab.birdvoice.databinding.FragmentRegisterBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                ViewObject(registerUsernameTitle),
                ViewObject(registerUsernameInput),
                ViewObject(registerPasswordTitle),
                ViewObject(registerPasswordInput),
                ViewObject(registerShowPasswordButton),
                ViewObject(registerCreateButton)
            )

            registerUsernameInput.filters = helpFunctions.editTextFilters
            registerPasswordInput.filters = helpFunctions.editTextFilters
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews, true)

        if (launchVM.boolPopBack) {
            launchVM.showTop()
        }
        binding.registerBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                    launchVM.hideTop()
                    errorViewOut(checkUsername = true, checkPassword = true)
                }
            }

            binding.registerCreateButton.setOnClickListener {
                checkRegister {
                    CheckUsernameClient.post(binding.registerUsernameInput.text!!, binding.registerPasswordInput.text!!, {
                        launchVM.getScope().launch {
                            delay(200)
                            animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                            launchVM.navigateToWithDelay(R.id.action_registerFragment_to_additionalFragment)
                            binding.registerCreateButton.isClickable = false
                            launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                            launchVM.setUsernameAndPassword(binding.registerUsernameInput.text!!, binding.registerPasswordInput.text!!)
                        }
                    }, {
                        activityLaunch.runOnUiThread { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                    })
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
            animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
            errorViewOut(checkUsername = true, checkPassword = true)
        }
    }

    private fun checkRegister(onSuccess: () -> Unit){
        var errorValue = 0

        setEditTextListeners()

        errorValue += helpFunctions.checkTextInput(binding.registerUsernameInput.text, binding.registerUsernameErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.registerPasswordInput.text, binding.registerPasswordErrorMessage, resources)

        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(checkUsername: Boolean = false, checkPassword: Boolean = false){
        if (checkUsername) helpFunctions.checkErrorViewAvailability(binding.registerUsernameErrorMessage)
        if (checkPassword) helpFunctions.checkErrorViewAvailability(binding.registerPasswordErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.registerUsernameInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkUsername = true) }, {}))
        binding.registerPasswordInput.addTextChangedListener(helpFunctions.createEditTextListener ({ errorViewOut(checkPassword = true) }, {}))
    }
}