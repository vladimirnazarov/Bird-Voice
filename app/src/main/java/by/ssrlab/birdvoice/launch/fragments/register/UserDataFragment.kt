package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentUserDataBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment
import coil.load
import coil.transform.CircleCropTransformation
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options

class UserDataFragment : BaseLaunchFragment() {

    private lateinit var binding: FragmentUserDataBinding
    private lateinit var cropImage: ActivityResultLauncher<CropImageContractOptions>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserDataBinding.inflate(layoutInflater)

        animVM.dataDefineElementsVisibility(binding)
        animVM.dataObjectEnter(MainApp.appContext, binding)

        registerCropImage()

        binding.userDataPhotoButton.animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                activityLaunch.setArrowAction {
                    animVM.dataObjectOut(MainApp.appContext, binding)
                    launchVM.navigateUpWithDelay()
                }

                binding.userDataPhotoButton.setOnClickListener {
                    pickPhoto()
                }

                binding.userDataApproveButton.setOnClickListener {
                    checkName {
                        Toast.makeText(MainApp.appContext, "Everything is fine!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback { animVM.dataObjectOut(MainApp.appContext, binding) }
    }

    private fun pickPhoto(){
        cropImage.launch(
            options {
                setAspectRatio(1, 1)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    private fun registerCropImage(){
        cropImage = registerForActivityResult(CropImageContract()){ result ->
            if (result.isSuccessful){
                val uriContent = result.uriContent
                if (uriContent != null) {
                    binding.userDataPhotoButton.load(uriContent) {
                        transformations(CircleCropTransformation())
                        size(320, 320)
                    }
                }
            } else {
                Toast.makeText(MainApp.appContext, result.error?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkName(onSuccess: () -> Unit){
        val errorAnim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_error_message_animation)

        setEditTextListener()

        if (binding.userDataNameInput.text?.isEmpty() == true){
            binding.userDataNameErrorMessage.text = resources.getString(R.string.this_field_must_be_not_empty)
            binding.userDataNameErrorMessage.startAnimation(errorAnim)
            binding.userDataNameErrorMessage.visibility = View.VISIBLE
        }
        else onSuccess()
    }

    private fun errorAnimOut(){
        val alphaOut = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.common_alpha_out)

        if (binding.userDataNameErrorMessage.visibility == View.VISIBLE){
            binding.userDataNameErrorMessage.startAnimation(alphaOut)
            binding.userDataNameErrorMessage.visibility = View.INVISIBLE
        }
    }

    private fun setEditTextListener(){
        binding.userDataNameInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorAnimOut()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}