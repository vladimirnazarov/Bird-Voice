package by.ssrlab.birdvoice.main.rv

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.CollectionDialogBinding
import by.ssrlab.birdvoice.databinding.CollectionRvItemBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM
import com.google.android.material.tabs.TabLayoutMediator

class CollectionAdapter(
    private val context: Context,
    private val mainVM: MainVM,
    private val activity: MainActivity,
    private val func: () -> Unit,
    private val tab1Text: String,
    private val tab2Text: String
): RecyclerView.Adapter<CollectionAdapter.CollectionHolder>() {

    private val viewArray = arrayListOf<CollectionRvItemBinding>()
    private val isOpenArray = arrayListOf<Boolean>()

    inner class CollectionHolder(val binding: CollectionRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val binding = CollectionRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        viewArray.add(binding)
        val viewImg = binding.collectionRvItemImage

        when (viewType){
            1 -> viewImg.setImageResource(R.drawable.ic_bird_collection_1)
            2 -> viewImg.setImageResource(R.drawable.ic_bird_collection_2)
            3 -> viewImg.setImageResource(R.drawable.ic_bird_collection_3)
            4 -> viewImg.setImageResource(R.drawable.ic_bird_collection_4)
            5 -> viewImg.setImageResource(R.drawable.ic_bird_collection_5)
            else -> viewImg.setImageResource(R.drawable.ic_bird_collection_2)
        }

        enterAnimation(binding.root)

        val pagerAdapter = CollectionPagerAdapter(activity)

        binding.apply {
            collectionRvItemButton.setOnClickListener {
                openView(viewArray.indexOf(binding))
            }

            collectionItemDelete.setOnClickListener {
                initDeletionDialog()
            }

            collectionRvItemOpenMapButton.setOnClickListener { func() }


            collectionRvItemOpenButton.setOnClickListener {
                closeViews()
            }

            collectionRvItemOpenPager.apply {
                adapter = pagerAdapter
                currentItem = 0
            }
            collectionRvItemOpenTabs.removeAllTabs()
        }

        TabLayoutMediator(binding.collectionRvItemOpenTabs, binding.collectionRvItemOpenPager){ tab, position ->
            if (position == 0) tab.text = tab1Text
            else if (position == 1) tab.text = tab2Text
        }.attach()

        return CollectionHolder(binding)
    }

    override fun getItemCount(): Int{
        val count = 6
        for (i in 0..count) isOpenArray.add(false)
        return count
    }

    override fun onBindViewHolder(holder: CollectionAdapter.CollectionHolder, position: Int) {
        val itemNumber = (position + 1).toString()
        holder.binding.collectionItemNumber.text = itemNumber

        mainVM.collectionObservable.observe(activity) {
            if (it) {
                for (i in viewArray) {
                    outAnimation(i.root)
                }
                for (i in isOpenArray){
                    if (i) {
                        mainVM.testMapTitle = viewArray[isOpenArray.indexOf(true)].collectionItemNumber.text as String
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = position + 1

    private fun enterAnimation(view: View){
        view.visibility = View.INVISIBLE
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_enter))
        view.visibility = View.VISIBLE
    }

    private fun outAnimation(view: View){
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_out))
        view.visibility = View.INVISIBLE
    }

    private fun openView(id: Int){
        closeViews()

        viewArray[id].apply {
            closureViewAnimation(this.collectionRvItemOpenImageHolder)
            closureViewAnimation(this.collectionRvItemOpenButtonHolder)
            closureViewAnimation(this.collectionRvItemOpenPager)
            closureViewAnimation(this.collectionRvItemOpenTabs)
            closureViewAnimation(this.collectionRvItemView2)
            closureViewAnimation(this.collectionRvItemOpenMapButton)

            closureViewAnimation(this.collectionRvItemButton, true)
            closureViewAnimation(this.collectionRvItemImage, true)
            closureViewAnimation(this.collectionItemNumber, true)
            closureViewAnimation(this.collectionItemDelete, true)
            closureViewAnimation(this.collectionRvItemView1, true)
        }

        isOpenArray[id] = true
    }

    private fun closeViews(){
        for (i in isOpenArray.indices) if (isOpenArray[i]) {
            viewArray[i].apply {
                closureViewAnimation(this.collectionRvItemOpenImageHolder, true)
                closureViewAnimation(this.collectionRvItemOpenButtonHolder, true)
                closureViewAnimation(this.collectionRvItemOpenPager, true)
                closureViewAnimation(this.collectionRvItemOpenTabs, true)
                closureViewAnimation(this.collectionRvItemView2, true)
                closureViewAnimation(this.collectionRvItemOpenMapButton, true)

                closureViewAnimation(this.collectionRvItemButton)
                closureViewAnimation(this.collectionRvItemImage)
                closureViewAnimation(this.collectionItemNumber)
                closureViewAnimation(this.collectionItemDelete)
                closureViewAnimation(this.collectionRvItemView1)
            }

            isOpenArray[i] = false
        }
    }

    private fun closureViewAnimation(view: View, close: Boolean = false){
        val alphaInAnimation = AnimationUtils.loadAnimation(context, R.anim.common_alpha_enter)
        val alphaOutAnimation = AnimationUtils.loadAnimation(context, R.anim.common_alpha_out)

        if (close){
            view.startAnimation(alphaOutAnimation)
            view.visibility = View.GONE
        } else {
            view.startAnimation(alphaInAnimation)
            view.visibility = View.VISIBLE
        }
    }

    private fun initDeletionDialog(){
        val dialog = Dialog(activity)
        val dialogBinding = CollectionDialogBinding.inflate(LayoutInflater.from(activity))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = activity.resources.displayMetrics.widthPixels - (activity.resources.displayMetrics.widthPixels / 10)
        dialog.window?.attributes = layoutParams
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var arrayOfViews1: ArrayList<ViewObject>
        var arrayOfViews2: ArrayList<ViewObject>
        dialogBinding.apply {
            arrayOfViews1 = arrayListOf(
                ViewObject(dialog1Bird),
                ViewObject(dialog1TopLeftCloud, "lc1"),
                ViewObject(dialog1TopRightCloud, "rc1"),
                ViewObject(dialog1Header),
                ViewObject(dialog1Text),
                ViewObject(dialog1ButtonHeader)
            )
            arrayOfViews2 = arrayListOf(
                ViewObject(dialog2Bird),
                ViewObject(dialog2Text),
                ViewObject(dialog2BottomRightCloud, "rc2"),
                ViewObject(dialog2TopRightCloud, "rc1"),
                ViewObject(dialog2TopLeftCloud, "lc1")
            )
        }

        val animUtils = by.ssrlab.birdvoice.helpers.utils.AnimationUtils()

        animUtils.commonDefineObjectsVisibility(arrayOfViews1)
        animUtils.commonObjectAppear(activity, arrayOfViews1, true)

        dialogBinding.dialog1No.setOnClickListener { dialog.dismiss() }
        dialogBinding.dialog1Yes.setOnClickListener {
            animUtils.commonObjectAppear(activity, arrayOfViews1)
            animUtils.commonObjectAppear(activity, arrayOfViews2, true)
        }

        dialog.show()
    }
}