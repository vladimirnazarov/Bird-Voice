package by.ssrlab.birdvoice.main.vm

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.ActivityMainBinding
import by.ssrlab.birdvoice.databinding.DialogLanguageBinding
import by.ssrlab.birdvoice.databinding.DialogLogOutBinding
import by.ssrlab.birdvoice.db.objects.RecognizedBird
import by.ssrlab.birdvoice.launch.LaunchActivity
import by.ssrlab.birdvoice.main.MainActivity
import kotlinx.coroutines.*
import java.io.File

class MainVM: ViewModel() {

    //Activity elements
    var activityBinding: ActivityMainBinding? = null
    private val toolbarTitle = MutableLiveData<String>()
    fun setToolbarTitle(title: String){
        toolbarTitle.value = title
    }
    fun setToolbarTitleObserver(toolbar: androidx.appcompat.widget.Toolbar, activity: MainActivity){
        toolbarTitle.observe(activity) {
            toolbar.title = it
        }
    }

    @Suppress("DEPRECATION")
    private fun initDialog(activity: MainActivity, dialogBindingAction: (Dialog) -> Unit) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        val dialog = Dialog(activity)
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

    fun setupDrawer(binding: ActivityMainBinding, activity: MainActivity) {
        binding.apply {
            drawerButtonLanguage.setOnClickListener {
                initDialog(activity) { dialog ->
                    val dialogBinding = DialogLanguageBinding.inflate(LayoutInflater.from(activity))
                    dialog.setContentView(dialogBinding.root)

                    dialogBinding.apply {
                        dialogLangButtonBy.setOnClickListener { initDrawerLanguageDialogAction("be", activity, dialog) }
                        dialogLangButtonEn.setOnClickListener { initDrawerLanguageDialogAction("en", activity, dialog) }
                        dialogLangButtonRu.setOnClickListener { initDrawerLanguageDialogAction("ru", activity, dialog) }
                    }
                }
            }

            drawerButtonInstruction.setOnClickListener { initDrawerButtonAction(R.id.informPageFragment, activity) }
            drawerButtonFeedback.setOnClickListener { initDrawerButtonAction(R.id.feedbackFragment, activity) }
            drawerButtonLogOut.setOnClickListener { initDrawerLogoutDialog(activity) }
        }
    }

    private fun initDrawerButtonAction(route: Int, activity: MainActivity) {
        navController.navigate(route)
        activity.hideBottomNav()
        activity.closeDrawer()
    }
    private fun initDrawerLanguageDialogAction(language: String, activity: MainActivity, dialog: Dialog) {
        dialog.dismiss()
        activity.closeDrawer()
        savePreferences(language, activity)
    }

    private fun initDrawerLogoutDialog(activity: MainActivity) {
        initDialog(activity) { dialog ->
            val dialogBinding = DialogLogOutBinding.inflate(LayoutInflater.from(activity))
            dialog.setContentView(dialogBinding.root)

            dialogBinding.apply {
                dialogLogOutNo.setOnClickListener { dialog.dismiss() }
                dialogLogOutYes.setOnClickListener {
                    dialog.dismiss()
                    activity.closeDrawer()

                    activity.getLoginManager().removeToken()
                    intentBack(activity)
                }
            }
        }
    }

    private fun intentBack(activity: MainActivity) {
        val intent = Intent(activity, LaunchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    private fun savePreferences(locale: String, activity: MainActivity) {
        val sharedPreferences = activity.getSharedPreferences(activity.getApp().constPreferences, AppCompatActivity.MODE_PRIVATE) ?: return
        with (sharedPreferences.edit()) {
            putString(activity.getApp().constLocale, locale)
            apply()
        }

        activity.recreate()
    }

    //Fragment elements
    val recognition2Value = MutableLiveData<Boolean>()
    val collectionObservable = MutableLiveData<Boolean>()
    var testMapTitle = ""
    var feedbackValue = 0

    //NavController set
    private lateinit var navController: NavController
    fun setNavController(controller: NavController){ navController = controller }
    fun navigateToWithDelay(address: Int){
        scope.launch {
            delay(600)
            navController.navigate(address)
        }
    }
    fun navigateUpWithDelay(){
        scope.launch {
            delay(600)
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
            navUpAnimLambda()
            navigateUpWithDelay()
        }
    }
    private var navUpAnimLambda = {}
    fun setNavUpLambda(anim: () -> Unit){
        navUpAnimLambda = anim
    }

    //AudioRecord
    private var tempAudioFile: File? = null
    fun getAudioFile() = tempAudioFile
    fun setAudioFile(file: File) { tempAudioFile = file }

    //Token
    private var token: String = ""
    fun getToken() = token
    fun setToken(token: String) { this.token = token }

    //Results
    private var listOfResults: ArrayList<RecognizedBird> = arrayListOf()
    fun getResults() = listOfResults
    fun setList(list: ArrayList<RecognizedBird>) { listOfResults = list }
}