package by.ssrlab.birdvoice.launch.vm

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.*

class LaunchVM: ViewModel() {

    //NavController set
    private lateinit var navController: NavController
    fun getNavController() = navController
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
}