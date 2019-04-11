package co.heri.monzo.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.heri.monzo.MainActivity
import co.heri.monzo.R
import kotlinx.android.synthetic.main.activity_login.*
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.zip.Inflater


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
//            startActivity(Intent(this, UnlockActivity::class.java))

            startModal()

        }

        register_link.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun startModal(title: String = "Title", message: String = "Modal Message", comfirm: Boolean = false){

        var layout = layoutInflater.inflate(R.layout.alert_dialog_layout, null)

        var dismiss_btn = layout.findViewById<Button>(R.id.modal_dismiss_btn)
        var comfirm_btn = layout.findViewById<Button>(R.id.modal_comfirm_btn)

        var title_text = layout.findViewById<TextView>(R.id.modal_title)
        var message_text = layout.findViewById<TextView>(R.id.modal_message)

        title_text.text = title
        message_text.text = message

        if(!comfirm){
            comfirm_btn.visibility = View.GONE
        }

        var dialog = AlertDialog.Builder(this)
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
