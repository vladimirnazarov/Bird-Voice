package by.ssrlab.birdvoice.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.ssrlab.birdvoice.helpers.HelpFunctions
import by.ssrlab.birdvoice.helpers.utils.AnimationUtils
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM

abstract class BaseMainFragment: Fragment() {

    val mainVM: MainVM by activityViewModels()
    val animationUtils = AnimationUtils()
    val helpFunctions = HelpFunctions()

    abstract var arrayOfViews: ArrayList<ViewObject>
    lateinit var activityMain: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMain = activity as MainActivity
    }

    fun navigationBackAction(action: () -> Unit){
        mainVM.navigateUpWithDelay()
        action()
    }
}