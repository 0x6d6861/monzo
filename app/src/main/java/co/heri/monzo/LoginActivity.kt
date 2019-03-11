package co.heri.monzo

import android.app.Activity
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.DialogFragment
import co.heri.monzo.utils.BiometricCallbackResponse
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executors

class LoginActivity : AppCompatActivity() {


    val executor = Executors.newSingleThreadExecutor()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val sampleString = StringBuilder()

        sampleString.append("Login to your account ");

        val account_text = "agape@live.fr"

        sampleString.append(account_text);

        val spannableString = SpannableString(sampleString.toString())

        spannableString.setSpan(StyleSpan(Typeface.BOLD),sampleString.length - account_text.length,sampleString.length,0);

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication")
            .setSubtitle(spannableString)
            .setDescription("Place your finger on the device home button to verify your identity")
            .setNegativeButtonText("Cancel")
            .build()


        val biometricPrompt = BiometricPrompt(this@LoginActivity, executor, BiometricCallbackResponse(this@LoginActivity))


        biometricPrompt.authenticate(promptInfo) // prompts the dialog by default

        fingerprint_btn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
