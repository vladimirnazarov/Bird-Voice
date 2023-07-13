package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentAdditionalBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

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
                ViewObject(additionalUsernameTitle),
                ViewObject(additionalUsernameInput),
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

            additionalUsernameInput.filters = helpFunctions.editTextFilters
            additionalFirstnameInput.filters = helpFunctions.editTextFilters
            additionalLastnameInput.filters = helpFunctions.editTextFilters
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        binding.additionalBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                    errorViewOut(checkUserName = true, checkFirstName = true, checkLastName = true)
                    launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                }
            }
        })

        binding.additionalCreateButton.setOnClickListener {
            checkAdditional {
                animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                Toast.makeText(MainApp.appContext, "Everything is fine!", Toast.LENGTH_SHORT).show()
                binding.additionalCreateButton.isClickable = false
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback {
            animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
            errorViewOut(checkUserName = true, checkFirstName = true, checkLastName = true)
        }
    }

    private fun checkAdditional(onSuccess: () -> Unit) {
        var errorValue = 0

        setEditTextListeners()

        errorValue += helpFunctions.checkTextInput(binding.additionalUsernameInput.text, binding.additionalUsernameErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.additionalFirstnameInput.text, binding.additionalFirstnameErrorMessage, resources)
        errorValue += helpFunctions.checkTextInput(binding.additionalLastnameInput.text, binding.additionalLastnameErrorMessage, resources)

        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(checkUserName: Boolean = false, checkFirstName: Boolean = false, checkLastName: Boolean = false) {
        if (checkUserName) helpFunctions.checkErrorViewAvailability(binding.additionalUsernameErrorMessage)
        if (checkFirstName) helpFunctions.checkErrorViewAvailability(binding.additionalFirstnameErrorMessage)
        if (checkLastName) helpFunctions.checkErrorViewAvailability(binding.additionalLastnameErrorMessage)
    }

    private fun setEditTextListeners(){
        binding.additionalUsernameInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut(checkUserName = true) }, {}))
        binding.additionalFirstnameInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut(checkFirstName = true) }, {}))
        binding.additionalLastnameInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut(checkLastName = true) }, {}))
    }
}