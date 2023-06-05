package by.ssrlab.birdvoice.main.rv

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ssrlab.birdvoice.main.fragments.recognition.recognition.tabs.RecognitionTab1
import by.ssrlab.birdvoice.main.fragments.recognition.recognition.tabs.RecognitionTab2

class RecognitionPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0 -> RecognitionTab1()

            1 -> RecognitionTab2()

            else -> RecognitionTab1()
        }
    }
}