package by.ssrlab.birdvoice.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.ActivityMainBinding
import by.ssrlab.birdvoice.main.vm.MainVM
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView

    private val mainVM: MainVM by viewModels()

    private var homeCallback = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            drawer = mainDrawerLayout
            bottomNav = mainBottomNavigationView
            mainVM.activityBinding = this

            mainVM.setRecController(mainContainerRecord.findNavController())
            mainVM.setColController(mainContainerCollection.findNavController())
            mainVM.setNavController()
        }
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        mainVM.setToolbarTitleObserver(binding.mainToolbar, this)
    }

    override fun onResume() {
        super.onResume()

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.record_nav -> {
                    if (mainVM.getActiveScreen() != "record"){
                        mainVM.switchNavController()
                        binding.apply {
                            mainContainerRecord.visibility = View.VISIBLE
                            mainContainerCollection.visibility = View.GONE
                        }
                        mainVM.setActiveScreen("record")
                    }
                    true
                }
                R.id.collection_nav -> {
                    if (mainVM.getActiveScreen() != "collection"){
                        mainVM.switchNavController()
                        binding.apply {
                            mainContainerRecord.visibility = View.GONE
                            mainContainerCollection.visibility = View.VISIBLE
                        }
                        mainVM.setActiveScreen("collection")
                    }
                    true
                }
                else -> true
            }
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