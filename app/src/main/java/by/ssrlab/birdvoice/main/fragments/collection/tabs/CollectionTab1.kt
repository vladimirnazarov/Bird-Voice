package by.ssrlab.birdvoice.main.fragments.collection.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.birdvoice.databinding.RvTab1Binding

class CollectionTab1: Fragment() {

    private lateinit var binding: RvTab1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RvTab1Binding.inflate(layoutInflater)

        binding.apply {
            tab1PlayerHolder.visibility = View.VISIBLE
        }

        return binding.root
    }
}