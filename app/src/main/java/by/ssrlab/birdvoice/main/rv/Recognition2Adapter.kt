package by.ssrlab.birdvoice.main.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM

class Recognition2Adapter(private val context: Context, private val mainVM: MainVM, private val activity: MainActivity): RecyclerView.Adapter<Recognition2Adapter.Recognition2Holder>() {

    private val viewArray = arrayListOf<View>()

    inner class Recognition2Holder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Recognition2Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recognition_2_rv_item, parent, false)

        viewArray.add(view)
        val viewImg = view.findViewById<ImageView>(R.id.recognition_2_rv_item_image)
        val viewPrc = view.findViewById<ImageView>(R.id.recognition_2_rv_item_percent)

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

        view.visibility = View.INVISIBLE
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_in))
        view.visibility = View.VISIBLE

        return Recognition2Holder(view)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: Recognition2Holder, position: Int) {
        mainVM.recognition2Value.observe(activity) {
            if (it == true) {
                for (i in viewArray) {
                    i.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_out))
                    i.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = position + 1
}