package by.ssrlab.birdvoice.main.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.Recognition2RvItemBinding
import by.ssrlab.birdvoice.helpers.RecognitionPagerAdapter
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM
import com.google.android.material.tabs.TabLayoutMediator

class Recognition2Adapter(
    private val context: Context,
    private val mainVM: MainVM,
    private val activity: MainActivity,
    private val tab1Text: String,
    private val tab2Text: String
    ) : RecyclerView.Adapter<Recognition2Adapter.Recognition2Holder>() {

    private val viewArray = arrayListOf<Recognition2RvItemBinding>()
    private val isOpenArray = arrayListOf<Boolean>()

    inner class Recognition2Holder(val binding: Recognition2RvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Recognition2Holder {
        val binding = Recognition2RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        viewArray.add(binding)
        val viewImg = binding.recognition2RvItemImage
        val viewPrc = binding.recognition2RvItemPercent

        when(viewType){
            1 -> {
                viewPrc.setImageResource(R.drawable.ic_80_percent_large)
                viewImg.setImageResource(R.drawable.ic_bird_card_2_large)
            }
            2 -> {
                viewPrc.setImageResource(R.drawable.ic_60_percent_large)
                viewImg.setImageResource(R.drawable.ic_bird_card_3_large)
            }
            3 -> {
                viewPrc.setImageResource(R.drawable.ic_60_percent_large)
                viewImg.setImageResource(R.drawable.ic_bird_card_1_large)
            }
            4 -> {
                viewPrc.setImageResource(R.drawable.ic_30_percent_large)
                viewImg.setImageResource(R.drawable.ic_bird_card_2_large)
            }
            5 -> {
                viewPrc.setImageResource(R.drawable.ic_30_percent_large)
                viewImg.setImageResource(R.drawable.ic_bird_card_3_large)
            }
            else -> {
                viewPrc.setImageResource(R.drawable.ic_30_percent_large)
                viewImg.setImageResource(R.drawable.ic_bird_card_1_large)
            }
        }

        enterAnimation(binding.root)

        binding.recognition2RvItemButton.setOnClickListener {
            openView(viewArray.indexOf(binding))
        }

        val pagerAdapter = RecognitionPagerAdapter(activity)
        binding.recognition2RvItemOpenPager.apply {
            adapter = pagerAdapter
            currentItem = 0
        }

        TabLayoutMediator(binding.recognition2RvItemOpenTabs, binding.recognition2RvItemOpenPager){tab, position ->
            if (position == 0) tab.text = tab1Text
            else if (position == 1) tab.text = tab2Text
        }.attach()

        return Recognition2Holder(binding)
    }

    override fun getItemCount(): Int{

        for (i in 0..5) isOpenArray.add(false)

        return 5
    }

    override fun onBindViewHolder(holder: Recognition2Holder, position: Int) {
        mainVM.recognition2Value.observe(activity) {
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
            closureViewAnimation(this.recognition2RvItemOpenImageHolder)
            closureViewAnimation(this.recognition2RvItemOpenButtonHolder)
            closureViewAnimation(this.recognition2RvItemOpenPager)
            closureViewAnimation(this.recognition2RvItemOpenTabs)
            closureViewAnimation(this.recognition2RvItemView2)

            closureViewAnimation(this.recognition2RvItemButton, true)
            closureViewAnimation(this.recognition2RvItemImage, true)
            closureViewAnimation(this.recognition2RvItemPercent, true)
            closureViewAnimation(this.recognition2RvItemView1, true)
        }

        isOpenArray[id] = true
    }

    private fun closeViews(){
        for (i in isOpenArray.indices) if (isOpenArray[i]) {
            viewArray[i].apply {
                closureViewAnimation(this.recognition2RvItemOpenImageHolder, true)
                closureViewAnimation(this.recognition2RvItemOpenButtonHolder, true)
                closureViewAnimation(this.recognition2RvItemOpenPager, true)
                closureViewAnimation(this.recognition2RvItemOpenTabs, true)
                closureViewAnimation(this.recognition2RvItemView2, true)

                closureViewAnimation(this.recognition2RvItemButton)
                closureViewAnimation(this.recognition2RvItemImage)
                closureViewAnimation(this.recognition2RvItemPercent)
                closureViewAnimation(this.recognition2RvItemView1)
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