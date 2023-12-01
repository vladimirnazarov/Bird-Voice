package by.ssrlab.birdvoice.main.rv

import android.content.Context
import android.view.*
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.databinding.Recognition2RvItemBinding
import by.ssrlab.birdvoice.db.objects.CollectionBird
import by.ssrlab.birdvoice.main.MainActivity
import by.ssrlab.birdvoice.main.vm.MainVM
import coil.load
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Recognition2Adapter(
    private val context: Context,
    private val mainVM: MainVM,
    private val activity: MainActivity,
    private val coroutineScope: CoroutineScope
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
                coroutineScope.launch {
                    getCurrentDate { date, time ->
                        val databaseObject = CollectionBird(
                            mainVM.getResults()[position].name,
                            mainVM.getResults()[position].image,
                            date, time)
                        activity.getCollectionDao().insert(databaseObject)
                    }
                }

                recognition2RvItemButton.apply {
                    setIconResource(R.drawable.ic_added)
                    text = ContextCompat.getString(activity, R.string.added)
                    isClickable = false
                }
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

    private fun getCurrentDate(onSuccess: (String, String) -> Unit) {
        val currentDateTime = LocalDateTime.now()

        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = currentDateTime.format(dateFormatter)

        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentDateTime.format(timeFormatter)

        onSuccess(formattedDate, formattedTime)
    }
}