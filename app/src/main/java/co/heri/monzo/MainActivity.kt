package co.heri.monzo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidstudy.daraja.Daraja
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import co.heri.monzo.utils.sms.MpesaMessage
import co.heri.monzo.utils.sms.MpesaParser
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.DarajaListener
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import co.heri.monzo.dialods.RequestDialog
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import android.app.Activity
import android.widget.TextView
import co.heri.monzo.Authentication.ProfileActivity
import co.heri.monzo.Authentication.SplashScreenActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.header_nav.*


class MainActivity : AppCompatActivity(), DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener {


    lateinit var daraja: Daraja;
    lateinit var phoneNumber: String;

    private lateinit var mTopToolbar: Toolbar

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser

        if(currentUser == null){
            startActivity(Intent(this, SplashScreenActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)

        updateUI(currentUser!!) // Updates the UI with the current useer detaisl

        mTopToolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        val actionbar: ActionBar? = supportActionBar

        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }


        drawerLayout = findViewById(R.id.drawer_layout)

        drawerLayout.addDrawerListener(this@MainActivity)

        var navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        request_dialog_btn.setOnClickListener {
            this@MainActivity.openRequestDialog()
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

        scan_btn.setOnClickListener {


        }


        val mpesaParser = MpesaParser();

        var MPESAValues = mutableListOf<MpesaMessage>()

        /*SMS.getSmsConversation(this, "MPESA"){ conversations ->
            conversations?.forEach { conversation ->

                conversation.message.forEach {

                    val parsed = mpesaParser.parse(it.body)

                    MPESAValues.add(parsed)


                }

               // println("Messages: ${conversation.message.toString()}")
            }
        }
*/


        //println(MPESAValues)

        scan_btn.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, ScanActivity::class.java), 1)
        }

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
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.notification_menu -> {
                Toast.makeText(this@MainActivity, "Action clicked", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private lateinit var request_dialog: RequestDialog
    private fun openRequestDialog(){
       request_dialog =  RequestDialog.display(supportFragmentManager)
    }

    fun OpenDrawer(view: View) {
        drawerLayout.openDrawer(GravityCompat.START)
    }


    // DRAWER EVENTS ARE HERE
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        // Respond when the drawer's position changes
    }

    override fun onDrawerOpened(drawerView: View) {
        // Respond when the drawer is opened
    }

    override fun onDrawerClosed(drawerView: View) {
        // Respond when the drawer is closed
    }

    override fun onDrawerStateChanged(newState: Int) {
        // Respond when the drawer motion state changes
    }
    // DRAWER EVENTS ARE HERE


fun setNumber(view: View){
    request_dialog.setNumber(view);
}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getStringExtra("result")

                Snackbar.make(scan_btn, result, Snackbar.LENGTH_SHORT).show()

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true

        when (menuItem.itemId) {
            R.id.logout -> {
                mAuth.signOut()
                startActivity(Intent(this, SplashScreenActivity::class.java))
                finishAffinity()
            }
            R.id.personal -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }

        drawerLayout.closeDrawers()

        return true

    }


    private fun updateUI(loggedInUser: FirebaseUser){

        var headerView = nav_view.getHeaderView(0)

        val drawer_email = headerView.findViewById<TextView>(R.id.drawer_email)
        val drawer_fullname = headerView.findViewById<TextView>(R.id.drawer_fullname)

        drawer_email.text = loggedInUser.email
        drawer_fullname.text = loggedInUser.displayName
    }

}
