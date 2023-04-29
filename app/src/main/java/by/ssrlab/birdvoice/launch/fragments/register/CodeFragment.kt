package by.ssrlab.birdvoice.launch.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.databinding.FragmentCodeConfirmationBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class CodeFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentCodeConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCodeConfirmationBinding.inflate(layoutInflater)

        return binding.root
    }
}