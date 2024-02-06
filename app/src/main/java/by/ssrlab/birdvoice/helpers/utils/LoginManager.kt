package by.ssrlab.birdvoice.helpers.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class LoginManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "login_prefs"
        private const val ACCESS_TOKEN = "access"
        private const val REFRESH_TOKEN = "refresh"
        private const val ACCOUNT_ID = "accountId"
        private const val KEY_LAST_LOGIN_DATE = "last_login_date"
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val EIGHT_HOURS = 8 * 60 * 60 * 1000
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isTokenValid(): Boolean {
        var token = ""
        getTokens { access, _, _ ->
            token = access
        }

        val lastLoginDate = getLastLoginDate()

        if (token.isNotEmpty() && lastLoginDate.isNotEmpty()) {
            val currentDate = getCurrentDate()

            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val lastLogin = dateFormat.parse(lastLoginDate)
            val current = dateFormat.parse(currentDate)

            if (current != null && lastLogin != null) {
                val timeDiff = current.time - lastLogin.time
                return if (timeDiff >= EIGHT_HOURS) {
                    removeTokens()
                    false
                } else {
                    true
                }
            }
        }

        return false
    }

    fun saveTokens(access: String, refresh: String, accountId: Int) {
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN, access)
        editor.putString(REFRESH_TOKEN, refresh)
        editor.putInt(ACCOUNT_ID, accountId)
        editor.putString(KEY_LAST_LOGIN_DATE, getCurrentDate())
        editor.apply()
    }

    fun getTokens(onSuccess: (String, String, Int) -> Unit) {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
        val accountId = sharedPreferences.getInt(ACCOUNT_ID, 0)

        onSuccess(accessToken, refreshToken, accountId)
    }

    private fun getLastLoginDate() = sharedPreferences.getString(KEY_LAST_LOGIN_DATE, "") ?: ""

    fun removeTokens() {
        val editor = sharedPreferences.edit()
        editor.apply {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(ACCOUNT_ID)
            remove(KEY_LAST_LOGIN_DATE)
            apply()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(Date())
    }
}