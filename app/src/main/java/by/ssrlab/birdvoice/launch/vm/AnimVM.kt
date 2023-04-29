package by.ssrlab.birdvoice.launch.vm

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentChoiceBinding
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import by.ssrlab.birdvoice.databinding.FragmentRegisterBinding

class AnimVM: ViewModel() {

    private lateinit var launchVM: LaunchVM
    fun setLaunchVM(launchVM: LaunchVM){
        this.launchVM = launchVM
    }

    //Choice
    fun choiceDefineElementsVisibility(binding: FragmentChoiceBinding){
        if (binding.choiceBird.visibility == View.VISIBLE) {
            binding.choiceBird.visibility = View.INVISIBLE
            binding.choiceLoginButton.visibility = View.INVISIBLE
            binding.choiceRegisterButton.visibility = View.INVISIBLE
            binding.choiceBottomLeftCloud.visibility = View.INVISIBLE
            binding.choiceBottomRightCloud.visibility = View.INVISIBLE
            binding.choiceTopRightCloud.visibility = View.INVISIBLE
        } else {
            binding.choiceBird.visibility = View.VISIBLE
            binding.choiceLoginButton.visibility = View.VISIBLE
            binding.choiceRegisterButton.visibility = View.VISIBLE
            binding.choiceBottomLeftCloud.visibility = View.VISIBLE
            binding.choiceBottomRightCloud.visibility = View.VISIBLE
            binding.choiceTopRightCloud.visibility = View.VISIBLE
        }
    }

    fun choiceObjectEnter(context: Context, binding: FragmentChoiceBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_enter_1)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_1)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_in)

        binding.choiceBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.choiceTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.choiceBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.choiceBird.startAnimation(alphaAnim)
        binding.choiceLoginButton.startAnimation(alphaAnim)
        binding.choiceRegisterButton.startAnimation(alphaAnim)

        choiceDefineElementsVisibility(binding)
    }

    fun choiceObjectOut(context: Context, binding: FragmentChoiceBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_out_1)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_1)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_out)

        binding.choiceBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.choiceTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.choiceBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.choiceBird.startAnimation(alphaAnim)
        binding.choiceLoginButton.startAnimation(alphaAnim)
        binding.choiceRegisterButton.startAnimation(alphaAnim)

        choiceDefineElementsVisibility(binding)
    }

    //Login
    fun loginDefineElementsVisibility(binding: FragmentLoginBinding){
        if (binding.loginBird.visibility == View.VISIBLE) {
            binding.loginWelcomeText.visibility = View.INVISIBLE
            binding.loginSignInText.visibility = View.INVISIBLE
            binding.loginBird.visibility = View.INVISIBLE
            binding.loginBottomLeftCloud.visibility = View.INVISIBLE
            binding.loginBottomRightCloud.visibility = View.INVISIBLE
            binding.loginTopRightCloud.visibility = View.INVISIBLE
            binding.loginEmailTitle.visibility = View.INVISIBLE
            binding.loginEmailInput.visibility = View.INVISIBLE
            binding.loginPasswordTitle.visibility = View.INVISIBLE
            binding.loginPasswordInput.visibility = View.INVISIBLE
            binding.loginRememberMe.visibility = View.INVISIBLE
            binding.loginForgotPassword.visibility = View.INVISIBLE
            binding.loginSignInButton.visibility = View.INVISIBLE
            binding.loginShowPasswordButton.visibility = View.INVISIBLE
        } else {
            binding.loginWelcomeText.visibility = View.VISIBLE
            binding.loginSignInText.visibility = View.VISIBLE
            binding.loginBird.visibility = View.VISIBLE
            binding.loginBottomLeftCloud.visibility = View.VISIBLE
            binding.loginBottomRightCloud.visibility = View.VISIBLE
            binding.loginTopRightCloud.visibility = View.VISIBLE
            binding.loginEmailTitle.visibility = View.VISIBLE
            binding.loginEmailInput.visibility = View.VISIBLE
            binding.loginPasswordTitle.visibility = View.VISIBLE
            binding.loginPasswordInput.visibility = View.VISIBLE
            binding.loginRememberMe.visibility = View.VISIBLE
            binding.loginForgotPassword.visibility = View.VISIBLE
            binding.loginSignInButton.visibility = View.VISIBLE
            binding.loginShowPasswordButton.visibility = View.VISIBLE
        }
    }

    fun loginObjectEnter(context: Context, binding: FragmentLoginBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_enter_1)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_2)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_in)

        binding.loginBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.loginBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.loginTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.loginWelcomeText.startAnimation(alphaAnim)
        binding.loginSignInText.startAnimation(alphaAnim)
        binding.loginBird.startAnimation(alphaAnim)
        binding.loginEmailTitle.startAnimation(alphaAnim)
        binding.loginEmailInput.startAnimation(alphaAnim)
        binding.loginPasswordTitle.startAnimation(alphaAnim)
        binding.loginPasswordInput.startAnimation(alphaAnim)
        binding.loginRememberMe.startAnimation(alphaAnim)
        binding.loginForgotPassword.startAnimation(alphaAnim)
        binding.loginSignInButton.startAnimation(alphaAnim)
        binding.loginShowPasswordButton.startAnimation(alphaAnim)

        loginDefineElementsVisibility(binding)
    }

    fun loginObjectOut(context: Context, binding: FragmentLoginBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_out_1)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_2)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_out)

        binding.loginBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.loginBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.loginTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.loginWelcomeText.startAnimation(alphaAnim)
        binding.loginSignInText.startAnimation(alphaAnim)
        binding.loginBird.startAnimation(alphaAnim)
        binding.loginEmailTitle.startAnimation(alphaAnim)
        binding.loginEmailInput.startAnimation(alphaAnim)
        binding.loginPasswordTitle.startAnimation(alphaAnim)
        binding.loginPasswordInput.startAnimation(alphaAnim)
        binding.loginRememberMe.startAnimation(alphaAnim)
        binding.loginForgotPassword.startAnimation(alphaAnim)
        binding.loginSignInButton.startAnimation(alphaAnim)
        binding.loginShowPasswordButton.startAnimation(alphaAnim)

        loginDefineElementsVisibility(binding)
    }

    //Register
    fun registerDefineElementsVisibility(binding: FragmentRegisterBinding){
        if (binding.registerBird.visibility == View.VISIBLE){
            binding.registerBird.visibility = View.INVISIBLE
            binding.registerBottomRightCloud.visibility = View.INVISIBLE
            binding.registerBottomLeftCloud.visibility = View.INVISIBLE
            binding.registerTopRightCloud.visibility = View.INVISIBLE
            binding.registerNewAccountText.visibility = View.INVISIBLE
            binding.registerSignUpText.visibility = View.INVISIBLE
            binding.registerEmailTitle.visibility = View.INVISIBLE
            binding.registerEmailInput.visibility = View.INVISIBLE
            binding.registerTelephoneTitle.visibility = View.INVISIBLE
            binding.registerTelephoneInput.visibility = View.INVISIBLE
            binding.registerPasswordTitle.visibility = View.INVISIBLE
            binding.registerPasswordInput.visibility = View.INVISIBLE
            binding.registerShowPasswordButton.visibility = View.INVISIBLE
            binding.registerPrivacyPolicy.visibility = View.INVISIBLE
            binding.registerCreateButton.visibility = View.INVISIBLE
        } else {
            binding.registerBird.visibility = View.VISIBLE
            binding.registerBottomRightCloud.visibility = View.VISIBLE
            binding.registerBottomLeftCloud.visibility = View.VISIBLE
            binding.registerTopRightCloud.visibility = View.VISIBLE
            binding.registerNewAccountText.visibility = View.VISIBLE
            binding.registerSignUpText.visibility = View.VISIBLE
            binding.registerEmailTitle.visibility = View.VISIBLE
            binding.registerEmailInput.visibility = View.VISIBLE
            binding.registerTelephoneTitle.visibility = View.VISIBLE
            binding.registerTelephoneInput.visibility = View.VISIBLE
            binding.registerPasswordTitle.visibility = View.VISIBLE
            binding.registerPasswordInput.visibility = View.VISIBLE
            binding.registerShowPasswordButton.visibility = View.VISIBLE
            binding.registerPrivacyPolicy.visibility = View.VISIBLE
            binding.registerCreateButton.visibility = View.VISIBLE
        }
    }

    fun registerObjectEnter(context: Context, binding: FragmentRegisterBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_enter_2)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_1)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_in)

        binding.registerBird.startAnimation(alphaAnim)
        binding.registerBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.registerBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.registerTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.registerNewAccountText.startAnimation(alphaAnim)
        binding.registerSignUpText.startAnimation(alphaAnim)
        binding.registerEmailTitle.startAnimation(alphaAnim)
        binding.registerEmailInput.startAnimation(alphaAnim)
        binding.registerTelephoneTitle.startAnimation(alphaAnim)
        binding.registerTelephoneInput.startAnimation(alphaAnim)
        binding.registerPasswordTitle.startAnimation(alphaAnim)
        binding.registerPasswordInput.startAnimation(alphaAnim)
        binding.registerShowPasswordButton.startAnimation(alphaAnim)
        binding.registerPrivacyPolicy.startAnimation(alphaAnim)
        binding.registerCreateButton.startAnimation(alphaAnim)

        registerDefineElementsVisibility(binding)
    }

    fun registerObjectOut(context: Context, binding: FragmentRegisterBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_out_2)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_2)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_out)

        binding.registerBird.startAnimation(alphaAnim)
        binding.registerBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.registerBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.registerTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.registerNewAccountText.startAnimation(alphaAnim)
        binding.registerSignUpText.startAnimation(alphaAnim)
        binding.registerEmailTitle.startAnimation(alphaAnim)
        binding.registerEmailInput.startAnimation(alphaAnim)
        binding.registerTelephoneTitle.startAnimation(alphaAnim)
        binding.registerTelephoneInput.startAnimation(alphaAnim)
        binding.registerPasswordTitle.startAnimation(alphaAnim)
        binding.registerPasswordInput.startAnimation(alphaAnim)
        binding.registerShowPasswordButton.startAnimation(alphaAnim)
        binding.registerPrivacyPolicy.startAnimation(alphaAnim)
        binding.registerCreateButton.startAnimation(alphaAnim)

        registerDefineElementsVisibility(binding)
    }
}