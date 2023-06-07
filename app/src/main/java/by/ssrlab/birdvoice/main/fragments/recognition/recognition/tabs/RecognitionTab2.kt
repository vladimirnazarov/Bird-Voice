package by.ssrlab.birdvoice.main.fragments.recognition.recognition.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.birdvoice.databinding.RvTab2Binding

class RecognitionTab2: Fragment() {

    private lateinit var binding: RvTab2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RvTab2Binding.inflate(layoutInflater)

        return binding.root
    }
}