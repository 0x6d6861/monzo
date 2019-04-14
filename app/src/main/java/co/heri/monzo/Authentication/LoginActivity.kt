package co.heri.monzo.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.heri.monzo.R
import kotlinx.android.synthetic.main.activity_login.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.heri.monzo.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()


        login_btn.setOnClickListener {

            val accountEmail = email_input.text.toString()
            val accountPassword = password_input.text.toString()

            if(accountPassword.isNullOrEmpty()){
                password_input.error = "Please confirm your password"
                return@setOnClickListener
            }

            if(accountEmail.isNullOrEmpty()){
                email_input.error = "Email address is required"
                return@setOnClickListener
            }

                mAuth.signInWithEmailAndPassword(accountEmail, accountPassword).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(this, "Welcome back " + mAuth.currentUser!!.displayName, Toast.LENGTH_SHORT).show()
                    } else {
                        password_input.error = "Invalid login details provided"
                        Toast.makeText(this, "Login faild, try again", Toast.LENGTH_SHORT).show()

                    }
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "OOps!! Something wrong happened", Toast.LENGTH_SHORT).show()
                    }



        }

        register_link.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun startModal(title: String = "Title", message: String = "Modal Message", comfirm: Boolean = false){

        val layout = layoutInflater.inflate(R.layout.alert_dialog_layout, null)

        val dismiss_btn = layout.findViewById<Button>(R.id.modal_dismiss_btn)
        val comfirm_btn = layout.findViewById<Button>(R.id.modal_comfirm_btn)

        val title_text = layout.findViewById<TextView>(R.id.modal_title)
        val message_text = layout.findViewById<TextView>(R.id.modal_message)

        title_text.text = title
        message_text.text = message

        if(!comfirm){
            comfirm_btn.visibility = View.GONE
        }

        val dialog = AlertDialog.Builder(this)
            .setView(layout)
            //.setTitle("Error")
            //.setMessage("An unknown network error has occured")
            //.setNegativeButton("Dismiss",
            //    DialogInterface.OnClickListener { dialog, which -> Log.d("MainActivity", "Aborting mission...") })
            .show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dismiss_btn.setOnClickListener {
            dialog.dismiss()
        }
    }
}
