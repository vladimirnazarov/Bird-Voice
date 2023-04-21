package by.ssrlab.birdvoice.launch.vm

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LaunchVM: ViewModel() {

    private lateinit var navController: NavController
    fun getNavController() = navController
    fun setNavController(controller: NavController){
        navController = controller
    }

    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
    fun getScope() = mediaScope
}