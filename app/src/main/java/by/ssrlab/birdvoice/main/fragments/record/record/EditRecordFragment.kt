package by.ssrlab.birdvoice.main.fragments.record.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.databinding.FragmentEditRecordBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment

class EditRecordFragment: BaseMainFragment() {

    private lateinit var binding: FragmentEditRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditRecordBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Recognition Service")
    }
}