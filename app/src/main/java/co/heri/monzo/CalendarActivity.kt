package co.heri.monzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.badoualy.datepicker.DatePickerTimeline
import java.util.*
import com.github.badoualy.datepicker.MonthView


class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val timeline = findViewById<DatePickerTimeline>(R.id.timeline) as DatePickerTimeline

        timeline.setDateLabelAdapter(object : MonthView.DateLabelAdapter {
            override fun getLabel(calendar: Calendar, index: Int): CharSequence {
                return Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) % 2000
            }
        })

        //timeline.setFirstVisibleDate(2018, Calendar.JANUARY, 19);
        //timeline.setLastVisibleDate(2020, Calendar.JANUARY, 19);



        timeline.onDateSelectedListener =
            DatePickerTimeline.OnDateSelectedListener { year, month, day, index ->
                run {
                    println(day)
                }
            }

    }
}
