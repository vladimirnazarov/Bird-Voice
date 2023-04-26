package by.ssrlab.birdvoice.launch.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentLoginBinding
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment

class LoginFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        animVM.loginDefineElementsVisibility(binding)
        animVM.loginObjectEnter(MainApp.appContext, binding)
        binding.loginArrowBack.setOnClickListener {
            launchVM.getNavController().popBackStack()
        }

        return binding.root
    }
}