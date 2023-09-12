package by.ssrlab.birdvoice.main.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.FragmentCollectionBinding
import by.ssrlab.birdvoice.db.objects.CollectionBird
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.rv.CollectionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CollectionFragment: BaseMainFragment() {

    private lateinit var binding: FragmentCollectionBinding
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    override var arrayOfViews = arrayListOf<ViewObject>()

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

        mainVM.setToolbarTitle(resources.getString(R.string.collection))

        scope.launch {
            val list = activityMain.getCollectionDao().getCollection() as ArrayList
            val reversedList = arrayListOf<CollectionBird>()

            for (i in list.size - 1 downTo 0) reversedList.add(list[i])

            activityMain.runOnUiThread {
                binding.collectionRv.apply {
                    layoutManager = LinearLayoutManager(activityMain.getApp().getContext())
                    adapter = CollectionAdapter(
                        activityMain,
                        reversedList,
                        scope
                    )
                }
            }
        }

        activityMain.setToolbarAction(R.drawable.ic_menu){ activityMain.openDrawer() }
    }
}