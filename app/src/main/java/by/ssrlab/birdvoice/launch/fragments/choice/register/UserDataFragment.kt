package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentUserDataBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
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
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserDataBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(userDataPhotoButton),
                ViewObject(userDataApproveButton),
                ViewObject(userDataNameInput),
                ViewObject(userDataNameTitle),
                ViewObject(userDataPhotoLabel)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        registerCropImage()

        binding.userDataPhotoButton.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                }
            }

            binding.userDataPhotoButton.setOnClickListener { pickPhoto() }
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

        activityLaunch.setPopBackCallback { animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews) }
    }

    private fun requestCameraPermission(onSuccess: () -> Unit){
        while (ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activityLaunch,
                arrayOf(Manifest.permission.CAMERA),
                1
            )
        }
        onSuccess()
    }

    private fun pickPhoto(){
        requestCameraPermission {
            cropImage.launch(
                options {
                    setAspectRatio(1, 1)
                    setCropShape(CropImageView.CropShape.OVAL)
                }
            )
        }
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

        errorValue += helpFunctions.checkTextInput(binding.userDataNameInput.text, binding.userDataNameErrorMessage, resources)
        if (errorValue == 0) onSuccess()
    }

    private fun errorViewOut(){
        helpFunctions.checkErrorViewAvailability(binding.userDataNameErrorMessage)
    }

    private fun setEditTextListener(){
        binding.userDataNameInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut() }, {}))
    }
}