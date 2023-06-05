package by.ssrlab.birdvoice.main.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.databinding.FragmentCollectionBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment

class CollectionFragment: BaseMainFragment() {

    private lateinit var binding: FragmentCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCollectionBinding.inflate(layoutInflater)

        activityMain.setPopBackCallback { mainVM.collectionValue.value = true }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
}