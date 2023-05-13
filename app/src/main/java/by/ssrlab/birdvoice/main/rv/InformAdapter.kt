package by.ssrlab.birdvoice.main.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.birdvoice.R

class InformAdapter : RecyclerView.Adapter<InformAdapter.InformHolder>() {

    inner class InformHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformHolder {
        val layoutResId = when (viewType) {
            ITEM_TYPE_1 -> R.layout.inform_rv_item_1
            ITEM_TYPE_2 -> R.layout.inform_rv_item_2
            ITEM_TYPE_3 -> R.layout.inform_rv_item_3
            ITEM_TYPE_4 -> R.layout.inform_rv_item_4
            ITEM_TYPE_5 -> R.layout.inform_rv_item_5
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return InformHolder(view)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: InformHolder, position: Int) {}

    override fun getItemViewType(position: Int): Int = position

    companion object {
        const val ITEM_TYPE_1 = 0
        const val ITEM_TYPE_2 = 1
        const val ITEM_TYPE_3 = 2
        const val ITEM_TYPE_4 = 3
        const val ITEM_TYPE_5 = 4
    }
}