package co.heri.monzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import co.heri.monzo.Authentication.SplashScreenActivity
import co.heri.monzo.Authentication.UnlockActivity
import co.heri.monzo.utils.Preferences
import com.google.firebase.auth.FirebaseAuth


class EmptyMainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent: Intent

        mAuth = FirebaseAuth.getInstance()


        // go straight to main if a token is stored
        if (mAuth.currentUser != null) {
            // TODO: if it the first run, start the splash screen
            activityIntent = Intent(this, UnlockActivity::class.java)
        } else {
//            activityIntent = Intent(this, UnlockActivity::class.java)
            activityIntent = Intent(this, SplashScreenActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}
