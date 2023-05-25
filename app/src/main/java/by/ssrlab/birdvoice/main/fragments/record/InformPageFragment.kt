package by.ssrlab.birdvoice.main.fragments.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentInformPageBinding
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.main.rv.InformAdapter

class InformPageFragment: BaseMainFragment() {

    private lateinit var binding: FragmentInformPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInformPageBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityMain.deletePopBackCallback()

        binding.informRv.apply {
            layoutManager = LinearLayoutManager(MainApp.appContext)
            adapter = InformAdapter(MainApp.appContext, mainVM)
        }

        mainVM.setToolbarTitle("How it works?")
        activityMain.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}