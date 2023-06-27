package by.ssrlab.birdvoice.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.ActivityMainBinding
import by.ssrlab.birdvoice.main.vm.MainVM
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    private var regValue = 0

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
            showBottomNav()

            mainVM.activityBinding = this

            setContentView(root)
            setSupportActionBar(mainToolbar)
            mainVM.setToolbarTitleObserver(mainToolbar, this@MainActivity)
        }

        regValue = intent.getIntExtra("userRegisterToken", 1)
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            drawerButtonLanguage.setOnClickListener { Toast.makeText(this@MainActivity, "Language", Toast.LENGTH_SHORT).show() }

            drawerButtonInstruction.setOnClickListener {
                navController.navigate(R.id.informPageFragment)
                hideBottomNav()
                closeDrawer()
            }

            drawerButtonFeedback.setOnClickListener {
                navController.navigate(R.id.feedbackFragment)
                hideBottomNav()
                closeDrawer()
            }

            drawerButtonInstagram.setOnClickListener { Toast.makeText(this@MainActivity, "Instagram", Toast.LENGTH_SHORT).show() }

            drawerButtonWhatsapp.setOnClickListener { Toast.makeText(this@MainActivity, "WhatsApp", Toast.LENGTH_SHORT).show() }

            drawerButtonTwitter.setOnClickListener { Toast.makeText(this@MainActivity, "Twitter", Toast.LENGTH_SHORT).show() }
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

    fun hideBottomNav(){
        if (bottomNav.visibility == View.VISIBLE){
            bottomNav.startAnimation(AnimationUtils.loadAnimation(MainApp.appContext, R.anim.nav_bottom_view_out))
            bottomNav.visibility = View.GONE
        }
    }

    fun showBottomNav(){
        if (bottomNav.visibility == View.GONE){
            bottomNav.startAnimation(AnimationUtils.loadAnimation(MainApp.appContext, R.anim.nav_bottom_view_enter))
            bottomNav.visibility = View.VISIBLE
        }
    }

    fun openDrawer(){
        drawer.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer(){
        drawer.closeDrawer(GravityCompat.START)
    }

    fun getRegValue() = regValue

    fun setRegValue(value: Int){
        regValue = value
    }
}