package by.ssrlab.birdvoice.helpers

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.helpers.utils.NoSpaceFilter
import by.ssrlab.birdvoice.launch.vm.LaunchVM

class HelpFunctions {

    val editTextFilters = arrayOf<InputFilter>(NoSpaceFilter())

    fun controlPopBack(launchVM: LaunchVM, bool: Boolean) {
        launchVM.boolPopBack = bool
        launchVM.boolArrowHide = bool
    }

    fun setPasswordShowButtonAction(
        passwordInput: AppCompatEditText,
        passwordShowButton: ImageButton
    ) {
        if (passwordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
            passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordShowButton.setImageResource(R.drawable.ic_launcher_show_password)
        } else {
            passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordShowButton.setImageResource(R.drawable.ic_launcher_hide_password)
        }
    }

    fun checkErrorViewAvailability(errorView: TextView) {
        val errorAlphaOut =
            AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_alpha_out)
        if (errorView.visibility == View.VISIBLE) {
            errorView.startAnimation(errorAlphaOut)
            errorView.visibility = View.INVISIBLE
        }
    }

    fun convertToTimerMode(duration: Int): String {
        val minute = duration % (1000 * 60 * 60) / (1000 * 60)
        val seconds = duration % (1000 * 60 * 60) % (1000 * 60) / 1000

        var finalString = ""
        finalString += "$minute:"
        if (seconds < 10) finalString += "0"
        finalString += "$seconds"

        return finalString
    }

    fun createEditTextListener(
        onTextChangedFun: () -> Unit,
        afterTextChangedFun: (Editable?) -> Unit
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChangedFun()
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChangedFun(s)
            }
        }
    }

    fun createAnimationEndListener(onAnimationEndFun: () -> Unit): Animation.AnimationListener {
        return object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEndFun()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        }
    }

    fun createSeekBarProgressListener(onProgressChangeFun: (Int) -> Unit): OnSeekBarChangeListener {
        return object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    onProgressChangeFun(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
    }

    fun checkTextInput(
        checkableText: Editable?,
        errorView: TextView,
        resources: Resources,
        errorText: String = resources.getString(R.string.this_field_must_be_not_empty)
    ): Int {
        val errorAnim =
            AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_error_message_animation)
        return if (checkableText?.isEmpty() == true) {
            errorView.text = errorText
            errorView.startAnimation(errorAnim)
            errorView.visibility = View.VISIBLE
            1
        } else 0
    }

    fun showKeyboard(view: View) {
        val imm =
            MainApp.appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(view: View?, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
