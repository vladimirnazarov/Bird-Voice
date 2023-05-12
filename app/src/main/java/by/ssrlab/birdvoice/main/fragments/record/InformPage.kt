package by.ssrlab.birdvoice.main.fragments.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.databinding.FragmentMainInformPageBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment

class InformPage: BaseMainFragment() {

    private lateinit var binding: FragmentMainInformPageBinding
//    private lateinit var adapter: InformPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainInformPageBinding.inflate(layoutInflater)

        return binding.root
    }
}