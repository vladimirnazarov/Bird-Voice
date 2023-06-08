package by.ssrlab.birdvoice.main.rv

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ssrlab.birdvoice.main.fragments.collection.tabs.CollectionTab1
import by.ssrlab.birdvoice.main.fragments.collection.tabs.CollectionTab2

class CollectionPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0 -> CollectionTab1()

            1 -> CollectionTab2()

            else -> CollectionTab1()
        }
    }
}