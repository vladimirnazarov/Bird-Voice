package by.ssrlab.birdvoice.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import by.ssrlab.birdvoice.helpers.AnimVM
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.BaseMainFragmentVM
import by.ssrlab.birdvoice.main.vm.MainVM

open class BaseMainFragment: Fragment() {

    val mainVM: MainVM by activityViewModels()
    val fragmentVM: BaseMainFragmentVM by activityViewModels()
    val animVM: AnimVM by activityViewModels()
    lateinit var activityMain: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMain = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainVM.setNavController(view.findNavController())
    }
}