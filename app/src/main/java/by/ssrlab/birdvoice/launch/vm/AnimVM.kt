package by.ssrlab.birdvoice.launch.vm

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentChoiceBinding
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding

class AnimVM: ViewModel() {

    private lateinit var launchVM: LaunchVM
    fun setLaunchVM(launchVM: LaunchVM){
        this.launchVM = launchVM
    }

    //Choice
    fun choiceDefineElementsVisibility(binding: FragmentChoiceBinding){
        if (binding.choiceBlueBird.visibility == View.VISIBLE) {
            binding.choiceBlueBird.visibility = View.INVISIBLE
            binding.choiceLoginButton.visibility = View.INVISIBLE
            binding.choiceRegisterButton.visibility = View.INVISIBLE
            binding.choiceBottomLeftCloud.visibility = View.INVISIBLE
            binding.choiceBottomRightCloud.visibility = View.INVISIBLE
            binding.choiceTopRightCloud.visibility = View.INVISIBLE
        } else {
            binding.choiceBlueBird.visibility = View.VISIBLE
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
        binding.choiceBlueBird.startAnimation(alphaAnim)
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
        binding.choiceBlueBird.startAnimation(alphaAnim)
        binding.choiceLoginButton.startAnimation(alphaAnim)
        binding.choiceRegisterButton.startAnimation(alphaAnim)

        choiceDefineElementsVisibility(binding)
    }

    //Login
    fun loginDefineElementsVisibility(binding: FragmentLoginBinding){
        if (binding.loginOrangeBird.visibility == View.VISIBLE) {
            binding.loginWelcomeText.visibility = View.INVISIBLE
            binding.loginSignInText.visibility = View.INVISIBLE
            binding.loginOrangeBird.visibility = View.INVISIBLE
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
            binding.loginOrangeBird.visibility = View.VISIBLE
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
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_1)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_in)

        binding.loginBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.loginBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.loginTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.loginWelcomeText.startAnimation(alphaAnim)
        binding.loginSignInText.startAnimation(alphaAnim)
        binding.loginOrangeBird.startAnimation(alphaAnim)
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
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_1)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_out_2)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_out)

        binding.loginBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.loginBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.loginTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.loginWelcomeText.startAnimation(alphaAnim)
        binding.loginSignInText.startAnimation(alphaAnim)
        binding.loginOrangeBird.startAnimation(alphaAnim)
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
}