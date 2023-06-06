package by.ssrlab.birdvoice.main.fragments.recognition.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentEditRecordBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.vm.PlayerVM

class EditRecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentEditRecordBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    private val playerVM: PlayerVM by viewModels()
    private var isPlaying = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (playerVM.checkIfBindingSaved()) {
            binding = FragmentEditRecordBinding.inflate(layoutInflater)
            playerVM.saveBinding(binding)
        } else binding = playerVM.getBinding()

        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(editRecConcaveTimeHolder),
                ViewObject(editRecConcaveDateHolder),
                ViewObject(editRecConcavePlaceHolder),
                ViewObject(editRecConcaveWaveHolder),
                ViewObject(editRecTopHolder),
                ViewObject(editRecBottomHolder),
                ViewObject(editRecPlayButton),
                ViewObject(editRecTimer),
                ViewObject(editRecAudioProgress),
                ViewObject(editRecLoadButton),
                ViewObject(editRecShareButton),
                ViewObject(editRecStartButton)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        binding.editRecTopHolder.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            binding.editRecStartButton.setOnClickListener {
                binding.editRecWaveAnimation.apply {
                    if (isPlaying){
                        pauseAnimation()
                        isPlaying = !isPlaying
                        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                        mainVM.navigateToWithDelay(R.id.action_editRecordFragment_to_recognitionFragment1)

                    } else {
                        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                        mainVM.navigateToWithDelay(R.id.action_editRecordFragment_to_recognitionFragment1)
                    }
                }
            }
        })

        activityMain.setPopBackCallback { animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlayer()
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Listen to your record")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction { animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews) }
        }
    }

    private fun setupPlayer() {
        if (mainVM.tempAudioFile != null) {
            playerVM.initializeMediaPlayer(mainVM.tempAudioFile!!.toUri())

            binding.editRecPlayButton.setOnClickListener { playerVM.playAudio() }
        }
    }
}