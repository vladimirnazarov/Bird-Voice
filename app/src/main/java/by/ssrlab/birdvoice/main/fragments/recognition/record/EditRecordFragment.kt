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
import by.ssrlab.birdvoice.helpers.createAnimationEndListener
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.vm.PlayerVM

class EditRecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentEditRecordBinding
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

        animVM.editRecDefineElementsVisibility(binding)
        animVM.editRecordObjectEnter(MainApp.appContext, binding)

        binding.editRecTopHolder.animation.setAnimationListener(createAnimationEndListener {
            binding.editRecStartButton.setOnClickListener {
                binding.editRecWaveAnimation.apply {
                    if (isPlaying){
                        pauseAnimation()
                        isPlaying = !isPlaying
                        animVM.editRecordObjectOut(MainApp.appContext, binding)
                        mainVM.navigateToWithDelay(R.id.action_editRecordFragment_to_recognitionFragment1)

                    } else {
                        animVM.editRecordObjectOut(MainApp.appContext, binding)
                        mainVM.navigateToWithDelay(R.id.action_editRecordFragment_to_recognitionFragment1)
                    }
                }
            }
        })

        activityMain.setPopBackCallback { animVM.editRecordObjectOut(MainApp.appContext, binding) }

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
            navigationBackAction({ animVM.editRecordObjectOut(MainApp.appContext, binding) }, {})
        }
    }

    private fun setupPlayer() {
        mainVM.tempAudioFile.apply {
            if (this != null) {
                playerVM.initializeMediaPlayer(this.toUri())

                binding.editRecPlayButton.setOnClickListener { playerVM.playAudio() }
            }
        }
    }
}