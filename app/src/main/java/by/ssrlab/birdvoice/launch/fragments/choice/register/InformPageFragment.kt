package by.ssrlab.birdvoice.launch.fragments.choice.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentInformPageBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.main.fragments.BaseMainFragment
import by.ssrlab.birdvoice.launch.rv.InformAdapter

class InformPageFragment: BaseMainFragment() {

    private lateinit var binding: FragmentInformPageBinding
    override var arrayOfViews = arrayListOf<ViewObject>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInformPageBinding.inflate(layoutInflater)

        activityMain.setPopBackCallback{}

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.informRv.apply {
            layoutManager = LinearLayoutManager(MainApp.appContext)
            adapter = InformAdapter(MainApp.appContext, mainVM)
        }

        mainVM.setToolbarTitle("How it works?")
        activityMain.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}