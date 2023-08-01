package by.ssrlab.birdvoice.main.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.Recognition2RvItemBinding
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM
import coil.load
import coil.transform.RoundedCornersTransformation

class Recognition2Adapter(
    private val context: Context,
    private val mainVM: MainVM,
    private val activity: MainActivity,
    ) : RecyclerView.Adapter<Recognition2Adapter.Recognition2Holder>() {

    private val viewArray = arrayListOf<Recognition2RvItemBinding>()

    inner class Recognition2Holder(val binding: Recognition2RvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Recognition2Holder {
        val binding = Recognition2RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        viewArray.add(binding)
        enterAnimation(binding.root)

        return Recognition2Holder(binding)
    }

    override fun getItemCount(): Int = mainVM.getResults().size

    override fun onBindViewHolder(holder: Recognition2Holder, position: Int) {

        holder.binding.apply {
            recognition2RvItemButton.setOnClickListener {
                Toast.makeText(context, "Will be soon", Toast.LENGTH_SHORT).show()
            }

            recognition2RvItemImage.load(mainVM.getResults()[position].image) {
                crossfade(true)
                transformations(RoundedCornersTransformation(16f))
            }

            val title = "${mainVM.getResults()[position].name} (${mainVM.getResults()[position].startTime}-${mainVM.getResults()[position].endTime} ${activity.resources.getText(R.string.sec)})"
            recognition2RvItemTitle.text = title
        }

        mainVM.recognition2Value.observe(activity) {
            if (it == true) for (i in viewArray) outAnimation(i.root)
        }
    }

    private fun enterAnimation(view: View){
        view.visibility = View.INVISIBLE
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_enter))
        view.visibility = View.VISIBLE
    }

    private fun outAnimation(view: View){
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.common_alpha_out))
        view.visibility = View.INVISIBLE
    }
}