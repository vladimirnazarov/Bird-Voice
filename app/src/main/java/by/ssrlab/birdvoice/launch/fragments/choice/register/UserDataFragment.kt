package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
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

        binding.userDataPhotoButton.animation.setAnimationListener(fragmentVM.createAnimationEndListener {
            launchVM.setArrowAction { navigationBackAction({ animVM.dataObjectOut(MainApp.appContext, binding) }, {}) }

            binding.userDataPhotoButton.setOnClickListener {
                pickPhoto()
            }

            binding.userDataApproveButton.setOnClickListener {
                checkName {
                    Toast.makeText(MainApp.appContext, "Everything is fine!", Toast.LENGTH_SHORT).show()
                }
            }
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
            }
        }
    }

    private fun checkName(onSuccess: () -> Unit){
        var errorValue = 0

        setEditTextListener()

        errorValue += fragmentVM.checkTextInput(binding.userDataNameInput.text, binding.userDataNameErrorMessage, resources)
        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(){
        fragmentVM.checkErrorViewAvailability(binding.userDataNameErrorMessage)
    }

    private fun setEditTextListener(){
        binding.userDataNameInput.addTextChangedListener(fragmentVM.createEditTextListener({ errorViewOut() }, {}))
    }
}