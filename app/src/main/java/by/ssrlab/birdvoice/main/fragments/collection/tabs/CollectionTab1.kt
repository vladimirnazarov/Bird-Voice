package by.ssrlab.birdvoice.main.fragments.collection.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.ssrlab.birdvoice.databinding.RvTab1Binding
import by.ssrlab.birdvoice.main.vm.MainVM

class CollectionTab1: Fragment() {

    private lateinit var binding: RvTab1Binding
    private val mainVM: MainVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RvTab1Binding.inflate(layoutInflater)

        binding.apply {
            tab1MapButton.visibility = View.VISIBLE
            tab1PlayerHolder.visibility = View.VISIBLE

            binding.tab1MapButton.setOnClickListener {
                mainVM.collectionObservable2.value = true
            }
        }

        return binding.root
    }
}