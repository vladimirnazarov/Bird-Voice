package by.ssrlab.birdvoice.main.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(collectionBird),
                ViewObject(collectionText),
                ViewObject(collectionTopRightCloud, "rc1"),
                ViewObject(collectionTopLeftCloud, "lc2"),
                ViewObject(collectionBottomLeftCloud, "lc1")
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(activityMain.getApp().getContext(), arrayOfViews, true)

        mainVM.setToolbarTitle(resources.getString(R.string.collection))

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        scope.launch {
            val list = activityMain.getCollectionDao().getCollection() as ArrayList
            val reversedList = arrayListOf<CollectionBird>()

            if (list != arrayListOf<CollectionBird>()) {
                val alphaOutAnim = AnimationUtils.loadAnimation(activityMain, R.anim.common_alpha_out)
                binding.apply {
                    collectionBird.startAnimation(alphaOutAnim)
                    collectionText.startAnimation(alphaOutAnim)

                    collectionBird.visibility = View.GONE
                    collectionText.visibility = View.GONE
                }
            }

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