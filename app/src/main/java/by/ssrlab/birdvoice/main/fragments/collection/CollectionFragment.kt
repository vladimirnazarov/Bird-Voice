package by.ssrlab.birdvoice.main.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentCollectionBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.rv.CollectionAdapter

class CollectionFragment: BaseMainFragment() {

    private lateinit var binding: FragmentCollectionBinding
    override var arrayOfViews = arrayListOf<ViewObject>()

    private val navFunc = {
        mainVM.collectionObservable.value = true
        mainVM.navigateToWithDelay(R.id.action_collectionFragment_to_mapFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCollectionBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.collectionRv.apply {
            layoutManager = LinearLayoutManager(MainApp.appContext)
            adapter = CollectionAdapter(
                MainApp.appContext,
                mainVM,
                activityMain,
                navFunc,
                resources.getString(R.string.general_information),
                resources.getString(R.string.scientific_information)
            )
        }

        mainVM.setToolbarTitle("Collection")
        activityMain.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}