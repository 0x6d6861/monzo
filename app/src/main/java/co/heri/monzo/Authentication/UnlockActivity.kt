package co.heri.monzo.Authentication

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.biometric.BiometricPrompt
import co.heri.monzo.MainActivity
import co.heri.monzo.R
import co.heri.monzo.utils.BiometricCallbackResponse
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executors

class UnlockActivity : AppCompatActivity() {


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


        val biometricPrompt = BiometricPrompt(this@UnlockActivity, executor, BiometricCallbackResponse(this@UnlockActivity))


        biometricPrompt.authenticate(promptInfo) // prompts the dialog by default

        fingerprint_btn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        proceed_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
