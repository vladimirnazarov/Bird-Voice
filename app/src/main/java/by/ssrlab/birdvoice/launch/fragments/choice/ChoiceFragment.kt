package by.ssrlab.birdvoice.launch.fragments.choice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentChoiceBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class ChoiceFragment : BaseLaunchFragment() {

    private lateinit var binding: FragmentChoiceBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChoiceBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(choiceBottomLeftCloud, "lc1"),
                ViewObject(choiceTopRightCloud, "rc1"),
                ViewObject(choiceBottomRightCloud, "rc2"),
                ViewObject(choiceBird),
                ViewObject(choiceLoginButton),
                ViewObject(choiceRegisterButton)
            )
        }

        activityLaunch.showStatusBar()
        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        binding.choiceBird.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            binding.choiceLoginButton.setOnClickListener {
                animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                binding.choiceLoginButton.isClickable = false
                launchVM.navigateToWithDelay(R.id.action_choiceFragment_to_loginFragment)
            }

            binding.choiceRegisterButton.setOnClickListener {
                animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                binding.choiceRegisterButton.isClickable = false
                launchVM.navigateToWithDelay(R.id.action_choiceFragment_to_registerFragment)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.deletePopBackCallback()
    }
}