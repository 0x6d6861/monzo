package co.heri.monzo.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import co.heri.monzo.MainActivity
import co.heri.monzo.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_unlock.view.*


class BiometricCallbackResponse(val context: Context) : BiometricPrompt.AuthenticationCallback() {


    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)

        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
            // user clicked negative button
        }

    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        // "Called when a biometric is recognized."

        if(Preferences.setToken(context)){
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        // "Called when a biometric is valid but not recognized."

        (context as Activity).apply {
            val passwordTXT = findViewById<TextInputEditText>(R.id.password)
            runOnUiThread {
                passwordTXT.error = "Failed to authenticate"
            }
        }
    }


}