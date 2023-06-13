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
            }
        }

        binding.feedbackInput.addTextChangedListener(helpFunctions.createEditTextListener({}){
            if (it?.isNotEmpty() == true){
                binding.feedbackSendButton.isClickable = true
                mainVM.feedbackValue = 1
                binding.feedbackSendButton.setOnClickListener {
                    animationUtils.commonObjectAppear(MainApp.appContext, toHideArray)
                    animationUtils.commonObjectAppear(MainApp.appContext, toShowArray, true)
                }
            } else {
                binding.feedbackSendButton.isClickable = false
                mainVM.feedbackValue = 0
            }
        })
    }
}