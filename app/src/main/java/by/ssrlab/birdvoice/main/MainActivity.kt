package by.ssrlab.birdvoice.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
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
import by.ssrlab.birdvoice.databinding.DialogLanguageBinding
import by.ssrlab.birdvoice.databinding.DialogLogOutBinding
import by.ssrlab.birdvoice.helpers.utils.LoginManager
import by.ssrlab.birdvoice.launch.LaunchActivity
import by.ssrlab.birdvoice.main.vm.MainVM
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    private var regValue = 0
    private var recognitionToken = ""

    private val mainApp = MainApp()
    private val mainVM: MainVM by viewModels()
    private lateinit var loginManager: LoginManager

    private var homeCallback = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        mainApp.setContext(this@MainActivity)
        loadPreferences()

        binding.apply {
            drawer = mainDrawerLayout

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
            navController = navHostFragment.navController
            mainVM.setNavController(navController)

            bottomNav = mainBottomNavigationView
            bottomNav.inflateMenu(R.menu.bottom_menu)
            bottomNav.setupWithNavController(navController)
            showBottomNav()

            mainVM.activityBinding = this

            setContentView(root)
            setSupportActionBar(mainToolbar)
            mainVM.setToolbarTitleObserver(mainToolbar, this@MainActivity)
        }

        loginManager = LoginManager(mainApp.getContext())

        regValue = intent.getIntExtra("userRegisterToken", 1)
        recognitionToken = intent.getStringExtra("token").toString()

        mainVM.setToken(recognitionToken)
    }

    override fun onResume() {
        super.onResume()

        setupDrawer()
    }

    @Suppress("DEPRECATION")
    private fun loadPreferences() {
        val sharedPreferences = getSharedPreferences(mainApp.constPreferences, MODE_PRIVATE)
        val locale = sharedPreferences.getString(mainApp.constLocale, "en")
        locale?.let { Locale(it) }?.let { mainApp.setLocale(it) }

        val config = mainApp.getContext().resources.configuration
        config.setLocale(Locale(locale!!))
        Locale.setDefault(Locale(locale))

        mainApp.getContext().resources.updateConfiguration(config, resources.displayMetrics)
        mainApp.setLocaleInt(locale)

        binding.apply {
            drawerSettingsLabel.setText(R.string.settings)
            drawerButtonLanguage.setText(R.string.language)
            drawerButtonFeedback.setText(R.string.feedback)
            drawerButtonInstruction.setText(R.string.instruction)
            drawerButtonLogOut.setText(R.string.log_out)
        }
    }

    private fun savePreferences(locale: String) {
        val sharedPreferences = getSharedPreferences(mainApp.constPreferences, MODE_PRIVATE) ?: return
        with (sharedPreferences.edit()) {
            putString(mainApp.constLocale, locale)
            apply()
        }

        recreate()
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
            bottomNav.startAnimation(AnimationUtils.loadAnimation(mainApp.getContext(), R.anim.nav_bottom_view_out))
            bottomNav.visibility = View.GONE
        }
    }

    fun showBottomNav(){
        if (bottomNav.visibility == View.GONE){
            bottomNav.startAnimation(AnimationUtils.loadAnimation(mainApp.getContext(), R.anim.nav_bottom_view_enter))
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

    private fun setupDrawer(){
        binding.apply {
            drawerButtonLanguage.setOnClickListener {
                initDialog { dialog ->
                    val dialogBinding = DialogLanguageBinding.inflate(LayoutInflater.from(this@MainActivity))
                    dialog.setContentView(dialogBinding.root)

                    dialogBinding.apply {
                        dialogLangButtonEn.setOnClickListener {
                            dialog.dismiss()
                            closeDrawer()
                            savePreferences("en") }
                        dialogLangButtonBy.setOnClickListener {
                            dialog.dismiss()
                            closeDrawer()
                            savePreferences("be") }
                        dialogLangButtonRu.setOnClickListener {
                            dialog.dismiss()
                            closeDrawer()
                            savePreferences("ru") }
                    }
                }
            }

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

            drawerButtonLogOut.setOnClickListener {
                initDialog { dialog ->
                    val dialogBinding = DialogLogOutBinding.inflate(LayoutInflater.from(this@MainActivity))
                    dialog.setContentView(dialogBinding.root)
                    dialogBinding.apply {
                        dialogLogOutNo.setOnClickListener { dialog.dismiss() }
                        dialogLogOutYes.setOnClickListener {
                            dialog.dismiss()
                            closeDrawer()

                            loginManager.removeToken()
                            intentBack()
                        }
                    }
                }
            }

            drawerButtonInstagram.setOnClickListener { Toast.makeText(this@MainActivity, "Instagram", Toast.LENGTH_SHORT).show() }

            drawerButtonWhatsapp.setOnClickListener { Toast.makeText(this@MainActivity, "WhatsApp", Toast.LENGTH_SHORT).show() }

            drawerButtonTwitter.setOnClickListener { Toast.makeText(this@MainActivity, "Twitter", Toast.LENGTH_SHORT).show() }
        }
    }

    @Suppress("DEPRECATION")
    private fun initDialog(dialogBindingAction: (Dialog) -> Unit) {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBindingAction(dialog)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = width - (width/5)
        dialog.window?.attributes = layoutParams

        dialog.show()
    }

    private fun intentBack() {
        val intent = Intent(this@MainActivity, LaunchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun getApp() = mainApp
}