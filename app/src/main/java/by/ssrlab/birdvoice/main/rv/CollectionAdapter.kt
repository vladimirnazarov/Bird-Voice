package by.ssrlab.birdvoice.main.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.CollectionRvItemBinding
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM

class CollectionAdapter(
    private val context: Context,
    private val mainVM: MainVM,
    private val activity: MainActivity,
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

        return CollectionHolder(binding)
    }

    override fun getItemCount(): Int{
        val count = 5
        for (i in 0..count) isOpenArray.add(false)
        return count
    }

    override fun onBindViewHolder(holder: CollectionAdapter.CollectionHolder, position: Int) {
        mainVM.collectionValue.observe(activity) {
            if (it == true) {
                for (i in viewArray) {
                    outAnimation(i.root)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = position + 1

    private fun enterAnimation(view: View){
        view.visibility = View.INVISIBLE
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_in))
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

            closureViewAnimation(this.collectionRvItemButton, true)
            closureViewAnimation(this.collectionRvItemImage, true)
            closureViewAnimation(this.collectionFavouriteHeart, true)
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

                closureViewAnimation(this.collectionRvItemButton)
                closureViewAnimation(this.collectionRvItemImage)
                closureViewAnimation(this.collectionFavouriteHeart)
                closureViewAnimation(this.collectionItemNumber)
                closureViewAnimation(this.collectionItemDelete)
                closureViewAnimation(this.collectionRvItemView1)
            }

            isOpenArray[i] = false
        }
    }

    private fun closureViewAnimation(view: View, close: Boolean = false){
        val alphaInAnimation = AnimationUtils.loadAnimation(context, R.anim.common_alpha_in)
        val alphaOutAnimation = AnimationUtils.loadAnimation(context, R.anim.common_alpha_out)

        if (close){
            view.startAnimation(alphaOutAnimation)
            view.visibility = View.GONE
        } else {
            view.startAnimation(alphaInAnimation)
            view.visibility = View.VISIBLE
        }
    }
}