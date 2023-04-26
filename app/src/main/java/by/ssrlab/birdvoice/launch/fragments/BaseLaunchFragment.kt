package by.ssrlab.birdvoice.launch.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import by.ssrlab.birdvoice.launch.LaunchActivity
import by.ssrlab.birdvoice.launch.vm.AnimVM
import by.ssrlab.birdvoice.launch.vm.LaunchVM

open class BaseLaunchFragment: Fragment() {

    val launchVM: LaunchVM by activityViewModels()
    val animVM: AnimVM by activityViewModels()
    lateinit var activityLaunch: LaunchActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLaunch = (activity as LaunchActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchVM.setNavController(view.findNavController())
    }
}