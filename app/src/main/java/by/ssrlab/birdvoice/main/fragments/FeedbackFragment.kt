package by.ssrlab.birdvoice.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp
import by.ssrlab.birdvoice.databinding.FragmentFeedbackBinding
import by.ssrlab.birdvoice.helpers.utils.ViewObject

class FeedbackFragment: BaseMainFragment() {

    private lateinit var binding: FragmentFeedbackBinding
    private lateinit var toHideArray: ArrayList<ViewObject>
    private lateinit var toShowArray: ArrayList<ViewObject>
    private lateinit var shownArray: ArrayList<ViewObject>
    override lateinit var arrayOfViews: ArrayList<ViewObject>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFeedbackBinding.inflate(layoutInflater)
        binding.apply {
            arrayOfViews = arrayListOf(
                ViewObject(feedback1Bird),
                ViewObject(feedbackInput),
                ViewObject(feedbackTitle),
                ViewObject(feedbackSendButton),
                ViewObject(feedbackBottomLeftCloud, "lc1"),
                ViewObject(feedbackTopRightCloud, "rc2"),
                ViewObject(feedbackBottomRightCloud, "rc1")
            )

            toHideArray = arrayListOf(
                ViewObject(feedbackTitle),
                ViewObject(feedbackInput),
                ViewObject(feedback1Bird),
                ViewObject(feedbackSendButton)
            )

            toShowArray = arrayListOf(
                ViewObject(feedback2Bird),
                ViewObject(feedbackCongratsHolder)
            )

            shownArray = arrayListOf(
                ViewObject(feedbackBottomLeftCloud, "lc1"),
                ViewObject(feedbackTopRightCloud, "rc2"),
                ViewObject(feedbackBottomRightCloud, "rc1"),
                ViewObject(feedback2Bird),
                ViewObject(feedbackCongratsHolder)
            )
        }

        animationUtils.commonDefineObjectsVisibility(arrayOfViews)
        animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews, true)

        activityMain.setPopBackCallback {
            if (mainVM.feedbackValue == 0) animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
            else {
                animationUtils.commonObjectAppear(MainApp.appContext, shownArray)
                mainVM.feedbackValue = 0
            }

            activityMain.showBottomNav()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainVM.setToolbarTitle("Feedback")
        activityMain.setToolbarAction(R.drawable.ic_arrow_back){
            navigationBackAction {
                if (mainVM.feedbackValue == 0) animationUtils.commonObjectAppear(MainApp.appContext, arrayOfViews)
                else {
                    animationUtils.commonObjectAppear(MainApp.appContext, shownArray)
                    mainVM.feedbackValue = 0
                }

                activityMain.showBottomNav()
            }
        }

        binding.feedbackSendButton.setOnClickListener {
            checkInput()

            if (binding.feedbackErrorMessage.visibility == View.INVISIBLE) {
                helpFunctions.hideKeyboard(view, MainApp.appContext)

                animationUtils.commonObjectAppear(MainApp.appContext, toHideArray)
                animationUtils.commonObjectAppear(MainApp.appContext, toShowArray, true)
            }
        }

        binding.feedbackInput.addTextChangedListener(helpFunctions.createEditTextListener({ errorViewOut() }){
            if (it?.isNotEmpty() == true) mainVM.feedbackValue = 1
            else mainVM.feedbackValue = 0
        })
    }

    private fun checkInput(){
        helpFunctions.checkTextInput(binding.feedbackInput.text, binding.feedbackErrorMessage, resources)
    }

    private fun errorViewOut(){
        helpFunctions.checkErrorViewAvailability(binding.feedbackErrorMessage)
    }
}