package by.ssrlab.birdvoice.main.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentMapBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment

class MapFragment: BaseMainFragment() {

    private lateinit var binding: FragmentMapBinding
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(mapMap),
                ViewObject(mapTestBirdId)
            )

            mapTestBirdId.text = mainVM.testMapTitle
            mainVM.collectionObservable.value = false
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews, true)

        activityMain.setPopBackCallback { animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Map")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction { animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews) }
        }
    }
}