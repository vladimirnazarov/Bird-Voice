package by.ssrlab.birdvoice.launch.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import by.ssrlab.birdvoice.helpers.utils.HelpFunctions
import by.ssrlab.birdvoice.helpers.utils.AnimationUtils
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.LaunchActivity
import by.ssrlab.birdvoice.launch.vm.LaunchVM

abstract class BaseLaunchFragment: Fragment() {

    val launchVM: LaunchVM by activityViewModels()
    val animationUtils = AnimationUtils()
    lateinit var helpFunctions: HelpFunctions

    abstract var arrayOfViews: ArrayList<ViewObject>
    lateinit var activityLaunch: LaunchActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLaunch = (activity as LaunchActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helpFunctions = HelpFunctions(activityLaunch.getApp())
        launchVM.setMainApp(activityLaunch.getApp())

        launchVM.setNavController(view.findNavController())
    }

    fun navigationBackAction(action: () -> Unit){
        launchVM.navigateUpWithDelay()
        action()
    }
}