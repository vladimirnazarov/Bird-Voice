package by.ssrlab.birdvoice.helpers.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class LoginManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "login_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_LAST_LOGIN_DATE = "last_login_date"
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val EIGHT_HOURS = 8 * 60 * 60 * 1000
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isTokenValid(): Boolean {
        val token = getToken()
        val lastLoginDate = getLastLoginDate()

        if (token.isNotEmpty() && lastLoginDate.isNotEmpty()) {
            val currentDate = getCurrentDate()

            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val lastLogin = dateFormat.parse(lastLoginDate)
            val current = dateFormat.parse(currentDate)

            if (current != null && lastLogin != null) {
                val timeDiff = current.time - lastLogin.time
                return if (timeDiff >= EIGHT_HOURS) {
                    removeToken()
                    false
                } else {
                    true
                }
            }
        }

        return false
    }

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_LAST_LOGIN_DATE, getCurrentDate())
        editor.apply()
    }

    fun getToken() = sharedPreferences.getString(KEY_TOKEN, "") ?: ""

    private fun getLastLoginDate() = sharedPreferences.getString(KEY_LAST_LOGIN_DATE, "") ?: ""

    private fun removeToken() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_TOKEN)
        editor.remove(KEY_LAST_LOGIN_DATE)
        editor.apply()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(Date())
    }
}