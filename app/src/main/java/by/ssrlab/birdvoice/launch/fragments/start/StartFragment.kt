package by.ssrlab.birdvoice.launch.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.client.loginization.LoginClient
import by.ssrlab.birdvoice.databinding.FragmentStartBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject
import by.ssrlab.birdvoice.launch.fragments.BaseLaunchFragment
import kotlinx.coroutines.*

class StartFragment: BaseLaunchFragment() {

    private lateinit var binding: FragmentStartBinding
    override var arrayOfViews = arrayListOf<ViewObject>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStartBinding.inflate(layoutInflater)

        activityLaunch.hideStatusBar()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        launchVM.getScope().launch {
            delay(3000)
            binding.logoLottie.animate().apply {
                duration = 500
                alpha(0f)

                withEndAction {
                    activityLaunch.apply {
                        getLoginManager().getTokens { login, password ->
                            if (login.isNotEmpty() && password.isNotEmpty()) {
                                LoginClient.post(login, password, { access, refresh, id ->
                                    moveToMainActivity(recognitionToken = access, refreshToken = refresh, accountId = id)
                                }, {
                                    activityLaunch.runOnUiThread {
                                        launchVM.navigate(R.id.action_logoFragment_to_choiceFragment)
                                    }
                                })
                            } else {
                                activityLaunch.runOnUiThread {
                                    launchVM.navigate(R.id.action_logoFragment_to_choiceFragment)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}