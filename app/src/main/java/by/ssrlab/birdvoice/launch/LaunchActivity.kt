package by.ssrlab.birdvoice.launch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.ActivityLaunchBinding
import by.ssrlab.birdvoice.launch.vm.LaunchVM

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private lateinit var controller: WindowInsetsControllerCompat
    private val launchVM: LaunchVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaunchBinding.inflate(layoutInflater)
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

    fun showArrow(){
        val arrowAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_left_obj_enter)
        binding.launcherArrowBack.startAnimation(arrowAnim)
        binding.launcherArrowBack.visibility = View.VISIBLE
    }

    fun hideArrow(){
        val arrowAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_left_obj_out)
        binding.launcherArrowBack.startAnimation(arrowAnim)
        binding.launcherArrowBack.visibility = View.INVISIBLE
    }

    fun getArrow() = binding.launcherArrowBack

    fun setArrowAction(action: () -> Unit){
        binding.launcherArrowBack.setOnClickListener {
            action()
        }
    }
}