package co.heri.monzo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import co.heri.monzo.fragments.transactions.AllTrasactionFragment
import co.heri.monzo.fragments.transactions.ReceivedTransactionFragment
import co.heri.monzo.fragments.transactions.SentTransactionFragment
import co.heri.monzo.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout








class TransactionActivity : AppCompatActivity(), AllTrasactionFragment.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {

        Toast.makeText(this@TransactionActivity, "Button clicked from fragment", Toast.LENGTH_SHORT).show()
    }

    private lateinit var mTopToolbar: Toolbar

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        mTopToolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.transaction_action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.transaction_filter_menu) {
            Toast.makeText(this@TransactionActivity, "Action clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AllTrasactionFragment(), "All")
        adapter.addFragment(ReceivedTransactionFragment(), "Received")
        adapter.addFragment(SentTransactionFragment(), "Sent")
        viewPager.adapter = adapter
    }


}
