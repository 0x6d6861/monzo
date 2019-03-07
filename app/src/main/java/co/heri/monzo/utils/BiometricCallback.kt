package co.heri.monzo.utils

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import co.heri.monzo.MainActivity
import com.an.biometric.BiometricCallback


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
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        // "Called when a biometric is valid but not recognized."
    }


}