package co.heri.monzo.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.heri.monzo.MainActivity
import co.heri.monzo.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            startActivity(Intent(this, UnlockActivity::class.java))
        }

        register_link.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
