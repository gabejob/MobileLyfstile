package com.example.lyfstile

import android.util.Patterns
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class FragmentUsernamePassViewModel : ViewModel() {

    fun onEmailEntered(email: String): Boolean {
        // check that the email entered is of the correct format
        return isValidEmail(email)
    }

    private fun isValidEmail(email: String): Boolean {
        if (!email.isNullOrEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true
            }
        }
        return false
    }

    fun onPasswordEntered(password: String): Boolean {
        // check that the password entered meets the correct criertia
        return isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean {
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        val pat = Pattern.compile(PASSWORD_PATTERN)
        if (!password.isNullOrEmpty() && pat.matcher(password).matches()) {
            return true
        }
        return false
    }

    fun isMatchingEmails(e1: String, e2: String): Boolean {
        if(isValidEmail(e1) && isValidEmail(e2)){
            if(e1.contentEquals(e2)){
                return true
            }
        }
        return false
    }

    fun isMatchingPasswords(p1: String, p2: String): Boolean {
        if(isValidPassword(p1) && isValidPassword(p2)){
            if(p1.contentEquals(p2)){
                return true
            }
        }
        return false
    }
}