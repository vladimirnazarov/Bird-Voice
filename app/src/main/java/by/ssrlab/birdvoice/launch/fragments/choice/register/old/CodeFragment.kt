package by.ssrlab.birdvoice.launch.fragments.choice.register.old

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentCodeConfirmationBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class CodeFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentCodeConfirmationBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCodeConfirmationBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(codeBird),
                ViewObject(codeBottomLeftCloud, "lc1"),
                ViewObject(codeBottomRightCloud, "rc1"),
                ViewObject(codeTopLeftCloud, "lc2"),
                ViewObject(codeEnter1),
                ViewObject(codeEnter2),
                ViewObject(codeEnter3),
                ViewObject(codeEnter4),
                ViewObject(codeCodeTitle),
                ViewObject(codeLoginButton),
                ViewObject(codeSendAgain)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        binding.codeBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                    errorViewOut()
                }
            }

            binding.codeLoginButton.setOnClickListener {
                checkCode {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                    launchVM.navigateToWithDelay(R.id.action_codeFragment_to_userDataFragment)
                    binding.codeLoginButton.isClickable = false
                    launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                }
            }

            setEditTextListeners()
        })

        helpFunctions.controlPopBack(launchVM, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback {
            animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
            errorViewOut()
        }
    }

    private fun checkCode(onSuccess: () -> Unit){
        val errorAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_error_message_animation)

        if (binding.codeEnter1.text?.isNotEmpty() == true && binding.codeEnter2.text?.isNotEmpty() == true && binding.codeEnter3.text?.isNotEmpty() == true && binding.codeEnter4.text?.isNotEmpty() == true){
            onSuccess()
        } else {
            binding.codeCodeErrorMessage.text = resources.getString(R.string.please_enter_the_code)
            binding.codeCodeErrorMessage.startAnimation(errorAnim)
            binding.codeCodeErrorMessage.visibility = View.VISIBLE
        }
    }

    private fun errorViewOut(){
        helpFunctions.checkErrorViewAvailability(binding.codeCodeErrorMessage)
    }

    private fun setEditTextListeners(){
        requestFocus(binding.codeEnter1)

        val etArray = arrayOf(binding.codeEnter1, binding.codeEnter2, binding.codeEnter3, binding.codeEnter4)

        etArray.forEachIndexed { index, appCompatEditText ->
            appCompatEditText.filters = helpFunctions.editTextFilters
            appCompatEditText.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut() }, {
                    if (it?.length == 1) {
                    if (index < etArray.size - 1) {
                        etArray[index + 1].text?.clear()
                        requestFocus(etArray[index + 1])
                    }
                    else {
                        helpFunctions.hideKeyboard(view, MainApp.appContext)
                        binding.codeEnter4.clearFocus()
                    }
                }
            }))
        }
    }

    private fun requestFocus(et: EditText){
        et.requestFocus()
        helpFunctions.showKeyboard(et)
    }
}