package by.ssrlab.birdvoice.launch.vm

import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.ActivityLaunchBinding
import kotlinx.coroutines.*

class LaunchVM: ViewModel() {

    //Activity Elements
    var boolPopBack = true
    var boolArrowHide = false
    var activityBinding: ActivityLaunchBinding? = null
    fun showArrow(){
        val arrowAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_left_obj_enter)
        activityBinding?.launcherArrowBack?.startAnimation(arrowAnim)
        activityBinding?.launcherArrowBack?.visibility = View.VISIBLE
    }
    fun hideArrow(){
        val arrowAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_left_obj_out)
        activityBinding?.launcherArrowBack?.startAnimation(arrowAnim)
        activityBinding?.launcherArrowBack?.visibility = View.INVISIBLE
    }

    //NavController set
    private lateinit var navController: NavController
    fun setNavController(controller: NavController){
        navController = controller
    }
    fun navigate(address: Int){
        navController.navigate(address)
    }
    fun navigateToWithDelay(address: Int){
        scope.launch {
            delay(1500)
            navigate(address)
        }
    }
    fun navigateUpWithDelay(){
        scope.launch {
            delay(1500)
            navController.popBackStack()
        }
    }

    //Scope
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    fun getScope() = scope

    //OnBackPressedCallback
    val onMapBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (activityBinding != null && boolArrowHide){
                hideArrow()
            }
            navUpAnimLambda()
            navigateUpWithDelay()
        }
    }
    private var navUpAnimLambda = {}
    fun setNavUpAnimLambda(anim: () -> Unit){
        navUpAnimLambda = anim
    }
}