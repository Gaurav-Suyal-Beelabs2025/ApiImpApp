package com.example.networkdemoapplication.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


object SharedPref {
    private var mSharedPref: SharedPreferences? = null
    const val NAME = "NAME"
    const val AGE = "AGE"
    const val IS_SELECT = "IS_SELECT"

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref?.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putString(key, value)
        prefsEditor?.apply()
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return mSharedPref!!.getBoolean(key, defValue)
    }

    fun write(key: String?, value: Boolean) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putBoolean(key, value)
        prefsEditor?.commit()
    }

    fun read(key: String?, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun write(key: String?, value: Int?) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putInt(key, value!!)?.commit()
    }
    fun clearKey(key: String?) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.remove(key)
        prefsEditor?.apply()
    }

    fun clearAllKeys() {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.clear()
        prefsEditor?.apply()
    }
    fun keyExists(key: String?):Boolean {

        if (mSharedPref?.contains(key) == true){
            return true
        }
        return false
    }
}