package by.ssrlab.birdvoice.launch.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import by.ssrlab.birdvoice.launch.LaunchActivity
import by.ssrlab.birdvoice.launch.vm.LaunchVM

open class BaseLaunchFragment: Fragment() {

    val launchVM: LaunchVM by activityViewModels()
    lateinit var mActivity: LaunchActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = (activity as LaunchActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchVM.setNavController(view.findNavController())
    }
}