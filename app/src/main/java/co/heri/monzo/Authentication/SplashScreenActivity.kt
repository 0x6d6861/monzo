package co.heri.monzo.Authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import co.heri.monzo.R
import androidx.viewpager.widget.ViewPager
import android.view.ViewGroup
import co.heri.monzo.MainActivity
import android.view.LayoutInflater
import co.heri.monzo.EmptyMainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val viewPager = findViewById(R.id.viewpager) as ViewPager
        viewPager.adapter = SplashPagerAdapter(this)

        login_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register_btn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }






    class SplashPagerAdapter(val context: Context) : PagerAdapter() {

        var titles = arrayOf("Screen One", "Screen Two")
        var layouts = intArrayOf(R.layout.screen_one, R.layout.screen_two)

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(layouts[position], container, false) as ViewGroup
            container.addView(layout)
            return layout
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
            return layouts.size;
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }


    }
}
