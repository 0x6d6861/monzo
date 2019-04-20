package co.heri.monzo.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import co.heri.monzo.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.EditText
import co.heri.monzo.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.UserProfileChangeRequest


class RegisterActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_register)

        fullname_input.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(!fullname_input.text.isNullOrBlank()){
                    fullname_input_layout.error = null
                } else {
                    fullname_input_layout.error = "Please provide your full name"
                }
            }

        })

        password_input.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(!password_input.text.isNullOrBlank()){
                    password_input_layout.error = null
                } else {
                    password_input_layout.error = "Please a password for your account"
                }
            }

        })


        email_input.addTextChangedListener(object: TextWatcher  {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(!email_input.text.isNullOrBlank()){
                    email_input_layout.error = null
                } else {
                    email_input_layout.error = "Email Address is required"
                }
                Is_Valid_Email(email_input);
            }

            fun Is_Valid_Email(edt: EditText) {
                if (edt.text.toString() == null) {
                    email_input.error = "Invalid Email Address"
//                        valid_email = null
                } else if (isEmailValid(edt.text.toString()) == false) {
                    email_input.error = "Invalid Email Address"
//                        valid_email = null
                } else {
//                        valid_email = edt.text.toString()
                    email_input.error = null
                }
            }

            fun isEmailValid(email: CharSequence): Boolean {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
            }

        })

        confirm_check.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                confirm_check.error = null
            } else {
                confirm_check.error = "Confirm terms of service"
            }
        }

        register_btn.setOnClickListener {

            val fullname = fullname_input.text.toString()
            val email = email_input.text.toString()
            val password = password_input.text.toString()
            val passwordConfirm = password_input_repeat.text.toString()
            val phone = phone_input.text.toString() // TODO: input validation




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

            if (password.compareTo(passwordConfirm) != 0 ){
                password_input_repeat.error = "Passwords provided do not match"
                return@setOnClickListener
            }

            if(!confirm_check.isChecked){
                confirm_check.error = "You need to agree to the terms of service"
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    val user = mAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(fullname).build()
                    user!!.updateProfile(profileUpdates).addOnCompleteListener {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                } else {
                    Snackbar.make(register_btn, "Registration failed, try later", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


}
