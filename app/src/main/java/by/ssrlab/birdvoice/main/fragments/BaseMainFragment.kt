package by.ssrlab.birdvoice.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.BaseMainFragmentVM
import by.ssrlab.birdvoice.main.vm.MainVM

open class BaseMainFragment: Fragment() {

    val mainVM: MainVM by activityViewModels()
    val fragmentVM: BaseMainFragmentVM by activityViewModels()
    lateinit var activityMain: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMain = activity as MainActivity
    }
}