package by.ssrlab.birdvoice.helpers

import android.text.InputFilter
import android.text.Spanned
import android.widget.Toast
import by.ssrlab.birdvoice.R
import by.ssrlab.birdvoice.app.MainApp

class NoSpaceFilter: InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        //Space
        for (i in start until end) {
            val c = source?.get(i)
            if (c == ' ') {
                Toast.makeText(MainApp.appContext, MainApp.appContext.getString(R.string.space_is_not_allowed), Toast.LENGTH_SHORT).show()
                return ""
            }
        }

        return null
    }
}
