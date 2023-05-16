package by.ssrlab.birdvoice.main.vm

import android.view.animation.Animation
import androidx.lifecycle.ViewModel

class BaseMainFragmentVM: ViewModel() {

    fun createAnimationEndListener(onAnimationEndFun: () -> Unit): Animation.AnimationListener {
        return object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEndFun()
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        }
    }
}