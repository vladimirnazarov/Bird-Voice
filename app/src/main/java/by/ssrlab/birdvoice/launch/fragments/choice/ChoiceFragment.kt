package by.ssrlab.birdvoice.launch.fragments.choice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.ssrlab.birdvoice.databinding.FragmentChoiceBinding
import by.ssrlab.birdvoice.launch.vm.LaunchVM

class ChoiceFragment: Fragment() {

    private lateinit var binding: FragmentChoiceBinding
    private val launchVM: LaunchVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChoiceBinding.inflate(layoutInflater)

        return binding.root
    }

    private fun defineElementsVisibility(){
        if (binding.choiceBlueBird.visibility == View.VISIBLE) {
            binding.choiceBlueBird.visibility = View.INVISIBLE
            binding.choiceLoginButton.visibility = View.INVISIBLE
            binding.choiceRegisterButton.visibility = View.INVISIBLE
            binding.choiceBottomLeftCloud.visibility = View.INVISIBLE
            binding.choiceBottomRightCloud.visibility = View.INVISIBLE
            binding.choiceTopRightCloud.visibility = View.INVISIBLE
        } else {
            binding.choiceBlueBird.visibility = View.VISIBLE
            binding.choiceLoginButton.visibility = View.VISIBLE
            binding.choiceRegisterButton.visibility = View.VISIBLE
            binding.choiceBottomLeftCloud.visibility = View.VISIBLE
            binding.choiceBottomRightCloud.visibility = View.VISIBLE
            binding.choiceTopRightCloud.visibility = View.VISIBLE
        }
    }
}