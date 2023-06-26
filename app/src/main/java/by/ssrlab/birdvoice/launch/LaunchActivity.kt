package by.ssrlab.birdvoice.launch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import by.ssrlab.birdvoice.databinding.ActivityLaunchBinding
import by.ssrlab.birdvoice.launch.vm.LaunchVM
import by.ssrlab.birdvoice.main.MainActivity

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private lateinit var controller: WindowInsetsControllerCompat
    private val launchVM: LaunchVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaunchBinding.inflate(layoutInflater)
        launchVM.activityBinding = binding
        setContentView(binding.root)

        controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    fun hideStatusBar(){
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun showStatusBar(){
        controller.show(WindowInsetsCompat.Type.systemBars())
    }

    fun setPopBackCallback(anim: () -> Unit){
        launchVM.setNavUpAnimLambda(anim)
        onBackPressedDispatcher.addCallback(this, launchVM.onMapBackPressedCallback)
    }

    fun deletePopBackCallback(){
        if (launchVM.onMapBackPressedCallback.isEnabled) launchVM.onMapBackPressedCallback.remove()
    }

    fun moveToMainActivity(token: Int = 0) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}