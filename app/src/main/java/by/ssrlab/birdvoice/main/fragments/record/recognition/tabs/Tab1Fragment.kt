package by.ssrlab.birdvoice.main.fragments.record.recognition.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.birdvoice.databinding.Recognition2Tab1Binding

class Tab1Fragment: Fragment() {

    private lateinit var binding: Recognition2Tab1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = Recognition2Tab1Binding.inflate(layoutInflater)

        return binding.root
    }
}