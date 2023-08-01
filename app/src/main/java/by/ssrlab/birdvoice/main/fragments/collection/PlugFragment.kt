package by.ssrlab.birdvoice.main.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.birdvoice.databinding.FragmentPlugBinding

class PlugFragment: Fragment() {

    private lateinit var binding: FragmentPlugBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlugBinding.inflate(layoutInflater)

        return binding.root
    }
}