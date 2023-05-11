package by.ssrlab.birdvoice.launch.fragments.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentCodeConfirmationBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class CodeFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentCodeConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCodeConfirmationBinding.inflate(layoutInflater)

        animVM.codeDefineElementsVisibility(binding)
        animVM.codeObjectEnter(MainApp.appContext, binding)

        binding.codeBird.animation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                activityLaunch.setArrowAction {
                    animVM.codeObjectOut(MainApp.appContext, binding)
                    launchVM.navigateUpWithDelay()
                }

                binding.codeLoginButton.setOnClickListener {
                    checkCode {
                        animVM.codeObjectOut(MainApp.appContext, binding)
                        launchVM.navigateToWithDelay(R.id.action_codeFragment_to_userDataFragment)
                        binding.codeLoginButton.isClickable = false
                        launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                    }
                }

                setEditTextListeners()
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        launchVM.boolPopBack = false
        launchVM.boolArrowHide = false

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.codeObjectOut(MainApp.appContext, binding) }
    }

    private fun showKeyboard(){
        val imm = MainApp.appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.codeEnter1, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(){
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setEditTextListeners(){
        val etArray = arrayOf(binding.codeEnter1, binding.codeEnter2, binding.codeEnter3, binding.codeEnter4)

        etArray.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    errorAnimationOut()
                }
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < etArray.size - 1) {
                            etArray[index + 1].requestFocus()
                            showKeyboard()
                        } else {
                            hideKeyboard()
                        }
                    }
                }
            })
        }

        etArray[0].requestFocus()
        showKeyboard()
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

    private fun errorAnimationOut(){
        val alphaOut = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_alpha_out)

        if (binding.codeCodeErrorMessage.visibility == View.VISIBLE) {
            binding.codeCodeErrorMessage.startAnimation(alphaOut)
            binding.codeCodeErrorMessage.visibility = View.INVISIBLE
        }
    }
}