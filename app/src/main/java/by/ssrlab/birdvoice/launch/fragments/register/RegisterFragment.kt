package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRegisterBinding
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

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        binding.registerBird.animation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.registerCreateButton.setOnClickListener {
                    checkRegister {
                        animVM.registerObjectOut(MainApp.appContext, binding)
                        launchVM.navigateToWithDelay(R.id.action_registerFragment_to_codeFragment)
                        binding.registerCreateButton.isClickable = false
                        launchVM.activityBinding?.launcherArrowBack?.isClickable = false
                    }
                }

                activityLaunch.setArrowAction {
                    animVM.registerObjectOut(MainApp.appContext, binding)
                    launchVM.hideArrow()
                    launchVM.navigateUpWithDelay()
                    errorAnimationOut(checkEmail = true, checkTelephone = true, checkPassword = true)
                }
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        launchVM.boolPopBack = true
        launchVM.boolArrowHide = true

        binding.registerShowPasswordButton.setOnClickListener {
            if (binding.registerPasswordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.registerPasswordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.registerShowPasswordButton.setImageResource(R.drawable.ic_launcher_show_password)
            } else {
                binding.registerPasswordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.registerShowPasswordButton.setImageResource(R.drawable.ic_launcher_hide_password)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.registerObjectOut(MainApp.appContext, binding) }
    }

    private fun checkRegister(onSuccess: () -> Unit){
        val errorAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_error_message_animation)

        var errorValue = 0

        setEditTextListeners()

        if (binding.registerEmailInput.text?.isEmpty() == true){
            binding.registerEmailErrorMessage.text = resources.getString(R.string.this_field_must_be_not_empty)
            binding.registerEmailErrorMessage.startAnimation(errorAnim)
            binding.registerEmailErrorMessage.visibility = View.VISIBLE
            errorValue += 1
        }

        if (binding.registerTelephoneInput.text?.isEmpty() == true){
            binding.registerTelephoneErrorMessage.text = resources.getString(R.string.this_field_must_be_not_empty)
            binding.registerTelephoneErrorMessage.startAnimation(errorAnim)
            binding.registerTelephoneErrorMessage.visibility = View.VISIBLE
            errorValue += 1
        }

        if (binding.registerPasswordInput.text?.isEmpty() == true){
            binding.registerPasswordErrorMessage.text = resources.getString(R.string.this_field_must_be_not_empty)
            binding.registerPasswordErrorMessage.startAnimation(errorAnim)
            binding.registerPasswordErrorMessage.visibility = View.VISIBLE
            errorValue += 1
        }

        if (errorValue == 0){
            onSuccess()
        }
    }

    private fun errorAnimationOut(checkEmail: Boolean = false, checkTelephone: Boolean = false, checkPassword: Boolean = false){
        val alphaOut = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_alpha_out)

        if (checkEmail) {
            if (binding.registerEmailErrorMessage.visibility == View.VISIBLE) {
                binding.registerEmailErrorMessage.startAnimation(alphaOut)
                binding.registerEmailErrorMessage.visibility = View.INVISIBLE
            }
        }

        if (checkTelephone){
            if (binding.registerTelephoneErrorMessage.visibility == View.VISIBLE){
                binding.registerTelephoneErrorMessage.startAnimation(alphaOut)
                binding.registerTelephoneErrorMessage.visibility = View.INVISIBLE
            }
        }

        if (checkPassword){
            if (binding.registerPasswordErrorMessage.visibility == View.VISIBLE){
                binding.registerPasswordErrorMessage.startAnimation(alphaOut)
                binding.registerPasswordErrorMessage.visibility = View.INVISIBLE
            }
        }
    }

    private fun setEditTextListeners(){
        binding.registerEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorAnimationOut(checkEmail = true)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.registerTelephoneInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorAnimationOut(checkTelephone = true)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.registerPasswordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorAnimationOut(checkPassword = true)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}