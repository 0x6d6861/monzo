package co.heri.monzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import co.heri.monzo.utils.Preferences


class EmptyMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent: Intent

        // go straight to main if a token is stored
        if (Preferences.getToken(this@EmptyMainActivity) != null) {
            activityIntent = Intent(this, MainActivity::class.java)
        } else {
            activityIntent = Intent(this, LoginActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}
