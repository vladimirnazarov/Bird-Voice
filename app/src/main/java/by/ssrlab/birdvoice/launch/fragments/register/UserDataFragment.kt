package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentUserDataBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment
import coil.load
import coil.transform.CircleCropTransformation
import com.canhub.cropper.*
import java.util.*

@Suppress("DEPRECATION")
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
                binding.userDataApproveButton.isClickable = true
                launchVM.activityBinding?.launcherArrowBack?.isClickable = true

                activityLaunch.setArrowAction {
                    animVM.dataObjectOut(MainApp.appContext, binding)
                    launchVM.navigateUpWithDelay()
                }

                binding.userDataPhotoButton.setOnClickListener {
                    pickPhoto()
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
                    }
                }
            } else {
                Toast.makeText(MainApp.appContext, result.error?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}