package co.heri.monzo.Authentication

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import co.heri.monzo.MainActivity
import co.heri.monzo.R
import co.heri.monzo.utils.BiometricCallbackResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_unlock.*
import java.util.concurrent.Executors



class UnlockActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private var currentUser: FirebaseUser? = null

    val executor = Executors.newSingleThreadExecutor()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlock)

        mAuth = FirebaseAuth.getInstance()

        currentUser = mAuth.currentUser

        if(currentUser == null ){
            var intent =  Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)

            finish()
        }



        val sampleString = StringBuilder()

        sampleString.append("Login to your account ");

        val accountEmail = currentUser!!.email

        fullname_text.text = currentUser!!.displayName

        email_text.text = accountEmail

        sampleString.append(accountEmail);

        val spannableString = SpannableString(sampleString.toString())

        if (accountEmail != null) { // TODO:: this should check in the preference if fingerprint auth is activated
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                sampleString.length - accountEmail.length,
                sampleString.length,
                0
            )

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


        };

        proceed_btn.setOnClickListener {

            val accountPassword = password_input.text.toString()

            if(accountPassword.isNullOrEmpty()){
                password_input.error = "Please confirm your password"
                return@setOnClickListener
            }

            if(accountEmail != null){

                mAuth.signInWithEmailAndPassword(accountEmail, accountPassword).addOnCompleteListener {
                    if(it.isSuccessful){
                        var intent =  Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this, "Welcome back " + currentUser!!.displayName, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        password_input.error = "Invalid login details provided"
                        Toast.makeText(this, "Login faild, try again", Toast.LENGTH_SHORT).show()

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "OOps!! Something wrong happened", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }
}
