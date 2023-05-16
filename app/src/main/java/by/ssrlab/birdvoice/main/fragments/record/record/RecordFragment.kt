package by.ssrlab.birdvoice.main.fragments.record.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentRecordBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment

class RecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentRecordBinding
    var pressedBool = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordBinding.inflate(layoutInflater)

        animVM.recDefineElementsVisibility(binding)
        animVM.recObjectEnter(MainApp.appContext, binding)

        binding.recBird.animation.setAnimationListener(fragmentVM.createAnimationEndListener {
            binding.recRecordButtonIcon.setOnClickListener {
                binding.recRecordButtonIcon.setImageResource(
                    if (pressedBool) R.drawable.ic_rec_stop
                    else R.drawable.ic_rec_start
                )

                pressedBool = !pressedBool
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Recording")
    }
}