package co.heri.monzo.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import co.heri.monzo.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern
import android.widget.EditText



class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_register)

        login_link.setOnClickListener {

            val fullname = fullname_input.text.toString()
            val email = email_input.text.toString()
            val password = password_input.text.toString()
            val passwordConfirm = password_input_repeat.text.toString()
            val phone = phone_input.text.toString() // TODO: input validation


            email_input.addTextChangedListener(object: TextWatcher  {
                override fun afterTextChanged(s: Editable?) {

                    Is_Valid_Email(email_input);

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                fun Is_Valid_Email(edt: EditText) {
                    if (edt.text.toString() == null) {
                        email_input_layout.error = "Invalid Email Address"
//                        valid_email = null
                    } else if (isEmailValid(edt.text.toString()) == false) {
                        email_input_layout.error = "Invalid Email Address"
//                        valid_email = null
                    } else {
//                        valid_email = edt.text.toString()
                    }
                }

                fun isEmailValid(email: CharSequence): Boolean {
                    return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches()
                }

            })


            if(fullname.isNullOrBlank()){
                fullname_input_layout.error = "Please provide your full name"
                return@setOnClickListener
            }

            if(email.isNullOrBlank()){
                email_input_layout.error = "Email address is required"
                return@setOnClickListener
            }

            if(password.isNullOrBlank()){
                password_input_layout.error = "Please provide a password"
                return@setOnClickListener
            }

            if(passwordConfirm.isNullOrBlank()){
                password_input_repeat.error = "Please repeat the password"
                return@setOnClickListener
            }

            if (password !== passwordConfirm){
                password_input_repeat.error = "Passwords provided do not match"
                return@setOnClickListener
            }


            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
