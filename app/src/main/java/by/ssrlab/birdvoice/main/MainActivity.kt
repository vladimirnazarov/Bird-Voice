package by.ssrlab.birdvoice.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.ActivityMainBinding
import by.ssrlab.birdvoice.main.vm.MainVM
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    private val mainVM: MainVM by viewModels()

    private var homeCallback = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            drawer = mainDrawerLayout

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
            navController = navHostFragment.navController
            mainVM.setNavController(navController)

            bottomNav = mainBottomNavigationView
            bottomNav.setupWithNavController(navController)

            mainVM.activityBinding = this

            setContentView(root)
            setSupportActionBar(mainToolbar)
            mainVM.setToolbarTitleObserver(mainToolbar, this@MainActivity)
        }
    }

    fun setToolbarAction(icon: Int, action: () -> Unit){
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(icon)
        }
        homeCallback = action
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                homeCallback()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setPopBackCallback(func: () -> Unit){
        mainVM.setNavUpLambda(func)
        onBackPressedDispatcher.addCallback(this, mainVM.onMapBackPressedCallback)
    }

    fun deletePopBackCallback(){
        if (mainVM.onMapBackPressedCallback.isEnabled) mainVM.onMapBackPressedCallback.remove()
    }

    fun openDrawer(){
        drawer.openDrawer(GravityCompat.START)
    }
}