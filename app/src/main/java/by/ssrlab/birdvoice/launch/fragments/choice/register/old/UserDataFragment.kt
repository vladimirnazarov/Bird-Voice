package by.ssrlab.birdvoice.launch.fragments.choice.register.old

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews, true)

        registerCropImage()

        binding.userDataPhotoButton.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            launchVM.setArrowAction {
                navigationBackAction {
                    animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
                    errorViewOut()
                }
            }

            binding.userDataPhotoButton.setOnClickListener { pickPhoto() }
            binding.userDataApproveButton.setOnClickListener {
                checkName {
//                    activityLaunch.moveToMainActivity(1)
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityLaunch.setPopBackCallback {
            animationUtils.commonObjectAppear(activityLaunch.getApp().getContext(), arrayOfViews)
            errorViewOut()
        }
    }

    private fun requestCameraPermission(onSuccess: () -> Unit){
        while (ContextCompat.checkSelfPermission(activityLaunch.getApp().getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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