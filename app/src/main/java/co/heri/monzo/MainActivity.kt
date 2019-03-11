package co.heri.monzo

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import com.androidstudy.daraja.Daraja
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.biometric.BiometricPrompt
import co.heri.monzo.utils.BiometricCallbackResponse
import co.heri.monzo.utils.sms.MpesaMessage
import co.heri.monzo.utils.sms.MpesaParser
import co.heri.monzo.utils.sms.SMS
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.DarajaListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import android.view.Menu
import android.view.MenuItem
import co.heri.monzo.dialods.RequestDialog


class MainActivity : AppCompatActivity() {

    lateinit var daraja: Daraja;
    lateinit var phoneNumber: String;

    private lateinit var mTopToolbar: Toolbar



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTopToolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        supportActionBar!!.setDisplayShowTitleEnabled(false);

        request_dialog_btn.setOnClickListener {
            this@MainActivity.openRequestDialod()
        }


        daraja =
            Daraja.with("UtktAROGXjf63QT5wT7yokvJ3K2xGSoY", "OGIb0ruJ8q8r1uXL", object : DarajaListener<AccessToken> {
                override fun onResult(accessToken: AccessToken) {
                    Log.i(this@MainActivity.javaClass.simpleName, accessToken.access_token)
                    Toast.makeText(this@MainActivity, "TOKEN : " + accessToken.access_token, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onError(error: String) {
                    Log.e(this@MainActivity.javaClass.simpleName, error)
                }
            })



        val mpesaParser = MpesaParser();

        var MPESAValues = mutableListOf<MpesaMessage>()

        SMS.getSmsConversation(this, "MPESA"){ conversations ->
            conversations?.forEach { conversation ->

                conversation.message.forEach {

                    val parsed = mpesaParser.parse(it.body)

                    MPESAValues.add(parsed)


                }

               // println("Messages: ${conversation.message.toString()}")
            }
        }



        println(MPESAValues)

        cal_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, TransactionActivity::class.java))
        }


        /*scan_btn.setOnClickListener {

            BiometricManager.BiometricBuilder(this@MainActivity)
                .setTitle("Authentication")
                .setSubtitle("Login to your Monzo account")
                .setDescription("Place your finger on the device home button to verify your identity")
                .setNegativeButtonText("Cancel")
                .build()
                .authenticate(BiometricCallbackResponse(this@MainActivity))

        }*/

        /*pay_btn.setOnClickListener {

            phoneNumber = phone.text.toString().trim()
            val amount_pay = amount.text.toString().trim()

            if(phoneNumber.isEmpty()){
                phone.error = "Please Provide a Phone Number"
                return@setOnClickListener
            }

            if(amount_pay.isEmpty()){
                amount.error = "Invalid amount"
                return@setOnClickListener
            }



            val lnmExpress = LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919", //https://developer.safaricom.co.ke/test_credentials
                TransactionType.CustomerBuyGoodsOnline, // TransactionType.CustomerPayBillOnline  <- Apply any of these two
                amount_pay,
                phoneNumber,
                "174379",
                phoneNumber,
                "https://weather-app-service.heriagape.repl.co/hooks/mpesa",
                "001ABC",
                "Goods Payment"
            );


            daraja.requestMPESAExpress(lnmExpress,
                object : DarajaListener<LNMResult> {
                    override fun onResult(lnmResult: LNMResult) {
                        Log.i(this@MainActivity.javaClass.simpleName, lnmResult.ResponseDescription)
                    }

                    override fun onError(error: String) {
                        Log.i(this@MainActivity.javaClass.simpleName, error)
                    }
                }
            )

        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.notification_menu) {
            Toast.makeText(this@MainActivity, "Action clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun openRequestDialod(){
        RequestDialog.display(supportFragmentManager)
    }


}
