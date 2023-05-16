package by.ssrlab.birdvoice.main.fragments.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.databinding.FragmentMainInformPageBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.rv.InformAdapter

class InformPage: BaseMainFragment() {

    private lateinit var binding: FragmentMainInformPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainInformPageBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.informRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = InformAdapter()
        }

        mainVM.setToolbarTitle("Map")
    }
}