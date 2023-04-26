package by.ssrlab.birdvoice.launch.vm

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentChoiceBinding
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    fun choiceObjectOut(context: Context, binding: FragmentChoiceBinding, address: Int){
        binding.choiceBottomLeftCloud.clearAnimation()
        binding.choiceTopRightCloud.clearAnimation()
        binding.choiceBottomRightCloud.clearAnimation()
        binding.choiceBlueBird.clearAnimation()
        binding.choiceLoginButton.clearAnimation()
        binding.choiceRegisterButton.clearAnimation()

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

        launchVM.getScope().launch {
            delay(1500)
            launchVM.navigate(address)
        }
    }

    //Login
    fun loginDefineElementsVisibility(binding: FragmentLoginBinding){
        if (binding.loginOrangeBird.visibility == View.VISIBLE) {
            binding.loginArrowBack.visibility = View.INVISIBLE
            binding.loginWelcomeText.visibility = View.INVISIBLE
            binding.loginSignIn.visibility = View.INVISIBLE
            binding.loginOrangeBird.visibility = View.INVISIBLE
            binding.loginBottomLeftCloud.visibility = View.INVISIBLE
            binding.loginBottomRightCloud.visibility = View.INVISIBLE
            binding.loginTopRightCloud.visibility = View.INVISIBLE
        } else {
            binding.loginArrowBack.visibility = View.VISIBLE
            binding.loginWelcomeText.visibility = View.VISIBLE
            binding.loginSignIn.visibility = View.VISIBLE
            binding.loginOrangeBird.visibility = View.VISIBLE
            binding.loginBottomLeftCloud.visibility = View.VISIBLE
            binding.loginBottomRightCloud.visibility = View.VISIBLE
            binding.loginTopRightCloud.visibility = View.VISIBLE
        }
    }

    fun loginObjectEnter(context: Context, binding: FragmentLoginBinding){
        val leftCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_cloud_enter_1)
        val rightTopCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_1)
        val rightBottomCloudAnim = AnimationUtils.loadAnimation(context, R.anim.common_right_cloud_enter_2)
        val leftObjAnim = AnimationUtils.loadAnimation(context, R.anim.common_left_obj_enter)
        val alphaAnim = AnimationUtils.loadAnimation(context, R.anim.common_alpha_in)

        binding.loginBottomLeftCloud.startAnimation(leftCloudAnim)
        binding.loginBottomRightCloud.startAnimation(rightBottomCloudAnim)
        binding.loginTopRightCloud.startAnimation(rightTopCloudAnim)
        binding.loginWelcomeText.startAnimation(alphaAnim)
        binding.loginSignIn.startAnimation(alphaAnim)
        binding.loginOrangeBird.startAnimation(alphaAnim)
        binding.loginArrowBack.startAnimation(leftObjAnim)

        loginDefineElementsVisibility(binding)
    }
}