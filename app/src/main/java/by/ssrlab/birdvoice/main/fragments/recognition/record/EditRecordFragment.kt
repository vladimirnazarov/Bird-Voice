package by.ssrlab.birdvoice.main.fragments.recognition.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentEditRecordBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.vm.PlayerVM

class EditRecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentEditRecordBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    private val playerVM: PlayerVM by viewModels()
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (playerVM.getRotationValue() != resources.configuration.orientation) playerVM.saveRotationValue(resources.configuration.orientation)
        else playerVM.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (playerVM.checkIfBindingSaved()) {
            binding = FragmentEditRecordBinding.inflate(layoutInflater)

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
                    ViewObject(editRecStartButton)
                )
            }

            playerVM.saveArrayOfViews(arrayOfViews)
        }
        else {
            binding = playerVM.getBinding()
            arrayOfViews = playerVM.getArrayOfViews()!!
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews, true)

        binding.editRecTopHolder.animation.setAnimationListener(helpFunctions.createAnimationEndListener {
            binding.editRecStartButton.setOnClickListener {
                binding.editRecWaveAnimation.apply {
                    if (isPlaying){
                        pauseAnimation()
                        isPlaying = !isPlaying
                        animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews)
                        mainVM.navigateToWithDelay(R.id.action_editRecordFragment_to_recognitionFragment1)

                    } else {
                        animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews)
                        mainVM.navigateToWithDelay(R.id.action_editRecordFragment_to_recognitionFragment1)
                    }
                }
            }
        })

        activityMain.setPopBackCallback { animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlayer()
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle(resources.getString(R.string.listen_to_your_record))
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction {
                animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews)
            }
        }

        binding.editRecConcaveTimeValue.text = helpFunctions.getCurrentTime()
        binding.editRecConcaveDateValue.text = helpFunctions.getCurrentDate()
        binding.editRecConcavePlaceValue.text = resources.getText(R.string.belarus)
    }

    override fun onStop() {
        super.onStop()

        playerVM.saveBinding(binding)
        if (playerVM.getRotationValue() != resources.configuration.orientation) playerVM.saveRotationValue(resources.configuration.orientation)
        else playerVM.clear()
    }

    private fun setupPlayer() {
        if (mainVM.getAudioFile() != null) {
            playerVM.initializeMediaPlayer(mainVM.getAudioFile()!!.toUri(), binding, activityMain.getApp())

            binding.editRecPlayButton.setOnClickListener { playerVM.playAudio(binding) }
        }
    }
}