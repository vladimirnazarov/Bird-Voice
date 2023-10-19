package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.ssrlab.birdvoice.client.loginization.LoginClient
import by.ssrlab.birdvoice.client.loginization.RegistrationClient
import by.ssrlab.birdvoice.databinding.FragmentAdditionalBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdditionalFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentAdditionalBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdditionalBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(additionalInformationText),
                ViewObject(additionalEmailTitle),
                ViewObject(additionalEmailInput),
                ViewObject(additionalFirstnameTitle),
                ViewObject(additionalFirstnameInput),
                ViewObject(additionalLastnameTitle),
                ViewObject(additionalLastnameInput),
                ViewObject(additionalBottomLeftCloud, "lc2"),
                ViewObject(additionalBottomRightCloud, "rc2"),
                ViewObject(additionalBird),
                ViewObject(additionalPrivacyPolicy),
                ViewObject(additionalCreateButton)
            )

            additionalPrivacyPolicy.movementMethod = LinkMovementMethod.getInstance()

            additionalEmailInput.filters = helpFunctions.editTextFilters
            additionalFirstnameInput.filters = helpFunctions.editTextFilters
            additionalLastnameInput.filters = helpFunctions.editTextFilters
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews, true)

        binding.additionalBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                    errorViewOut(checkEmail = true, checkFirstName = true, checkLastName = true)
                    launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                }
            }
        })

        binding.additionalCreateButton.setOnClickListener {
            checkAdditional {
                RegistrationClient.post(launchVM.getUsername(), launchVM.getPassword(), binding.additionalFirstnameInput.text!!, binding.additionalLastnameInput.text!!, binding.additionalEmailInput.text!!, {

                    //OnSuccess
                    LoginClient.post(launchVM.getUsername(), launchVM.getPassword(), {
                        launchVM.getScope().launch {
                            delay(200)
                            activityLaunch.moveToMainActivity(recognitionToken = it)
                            animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                            binding.additionalCreateButton.isClickable = false
                        }
                    }, {
                        activityLaunch.runOnUiThread { Toast.makeText(activityLaunch, it, Toast.LENGTH_SHORT).show() }
                    })

                    },

                    //OnFailure
                    {
                        activityLaunch.runOnUiThread { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                    }
                )
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback {
            animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
            errorViewOut(checkEmail = true, checkFirstName = true, checkLastName = true)
        }
    }

    private fun checkAdditional(onSuccess: () -> Unit) {
        var errorValue = 0

        setEditTextListeners()

        errorValue += helpFunctions.checkTextInput(binding.additionalEmailInput.text, binding.additionalEmailErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.additionalFirstnameInput.text, binding.additionalFirstnameErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.additionalLastnameInput.text, binding.additionalLastnameErrorMessage, resources)

        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(checkEmail: Boolean = false, checkFirstName: Boolean = false, checkLastName: Boolean = false) {
        if (checkEmail) helpFunctions.checkErrorViewAvailability(binding.additionalEmailErrorMessage)
        if (checkFirstName) helpFunctions.checkErrorViewAvailability(binding.additionalFirstnameErrorMessage)
        if (checkLastName) helpFunctions.checkErrorViewAvailability(binding.additionalLastnameErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.additionalEmailInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut(checkEmail = true) }, {}))
        binding.additionalFirstnameInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut(checkFirstName = true) }, {}))
        binding.additionalLastnameInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut(checkLastName = true) }, {}))
    }
}