package by.ssrlab.birdvoice.launch.fragments.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class LoginFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        animVM.loginDefineElementsVisibility(binding)
        animVM.loginObjectEnter(MainApp.appContext, binding)

        if (launchVM.boolPopBack) {
            launchVM.showArrow()
        }
        activityLaunch.setArrowAction {
            animVM.loginObjectOut(MainApp.appContext, binding)
            launchVM.hideArrow()
            launchVM.navigateUpWithDelay()
            errorAnimationOut(checkLogin = true, checkPassword = true)
        }

        launchVM.boolPopBack = true
        launchVM.boolArrowHide = true

        binding.loginShowPasswordButton.setOnClickListener {
            if (binding.loginPasswordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.loginPasswordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.loginShowPasswordButton.setImageResource(R.drawable.ic_launcher_show_password)
            } else {
                binding.loginPasswordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.loginShowPasswordButton.setImageResource(R.drawable.ic_launcher_hide_password)
            }
        }

        binding.loginBird.animation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.loginSignInButton.isClickable = true
                binding.loginSignInButton.setOnClickListener {
                    checkLogin()
                }
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.loginObjectOut(MainApp.appContext, binding) }
    }

    private fun checkLogin(){
        val errorAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_error_message_animation)

        setEditTextListeners()

        if (binding.loginEmailInput.text?.isEmpty() == true){
            binding.loginEmailErrorMessage.text = resources.getString(R.string.this_field_must_be_not_empty)
            binding.loginEmailErrorMessage.startAnimation(errorAnim)
            binding.loginEmailErrorMessage.visibility = View.VISIBLE
        }

        if (binding.loginPasswordInput.text?.isEmpty() == true){
            binding.loginPasswordErrorMessage.text = resources.getString(R.string.this_field_must_be_not_empty)
            binding.loginPasswordErrorMessage.startAnimation(errorAnim)
            binding.loginPasswordErrorMessage.visibility = View.VISIBLE
        }
    }

    private fun errorAnimationOut(checkLogin: Boolean = false, checkPassword: Boolean = false){
        val alphaOut = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_alpha_out)

        if (checkLogin) {
            if (binding.loginEmailErrorMessage.visibility == View.VISIBLE) {
                binding.loginEmailErrorMessage.startAnimation(alphaOut)
                binding.loginEmailErrorMessage.visibility = View.INVISIBLE
            }
        }

        if (checkPassword){
            if (binding.loginPasswordErrorMessage.visibility == View.VISIBLE){
                binding.loginPasswordErrorMessage.startAnimation(alphaOut)
                binding.loginPasswordErrorMessage.visibility = View.INVISIBLE
            }
        }
    }

    private fun setEditTextListeners(){
        binding.loginEmailInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorAnimationOut(checkLogin = true)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.loginPasswordInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorAnimationOut(checkPassword = true)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}