package co.heri.monzo.utils

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.sql.Date
import java.sql.Timestamp


object Preferences {

    fun getToken(context: Context): String? {
        val sharedPref = (context as Activity).getSharedPreferences("authentication", Context.MODE_PRIVATE)
        return sharedPref.getString("token", null);
    }

    fun setToken(context: Context): Boolean {

        val sharedPref = (context as Activity).getSharedPreferences("authentication", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("token", Timestamp(System.currentTimeMillis()).time.toString())
        editor.apply()
        return true;
    }


    fun deleteToken(context: Context){
        val sharedPref = (context as Activity).getSharedPreferences("authentication", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("token");
        editor.apply();
    }


}