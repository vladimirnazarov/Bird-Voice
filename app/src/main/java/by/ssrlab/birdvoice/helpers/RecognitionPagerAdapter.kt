package by.ssrlab.birdvoice.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ssrlab.birdvoice.main.fragments.record.recognition.tabs.Tab1Fragment
import by.ssrlab.birdvoice.main.fragments.record.recognition.tabs.Tab2Fragment

class RecognitionPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0 -> Tab1Fragment()

            1 -> Tab2Fragment()

            else -> Tab1Fragment()
        }
    }
}