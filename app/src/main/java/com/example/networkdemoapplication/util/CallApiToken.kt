package com.example.networkdemoapplication.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class CallApiToken(val context: Context) {
    lateinit var app : Application
    private val sharedPreferences : SharedPreferences= context.getSharedPreferences("Get Token", Context.MODE_PRIVATE)

    fun fetch(KEY_NAME : String, text: String){
        val fetchToken: SharedPreferences.Editor = sharedPreferences.edit()
        fetchToken.putString(KEY_NAME, text)
        fetchToken.apply()
    }

       fun save(KEY_NAME : String, value: Int){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun save(KEY_NAME : String, status: Boolean){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NAME, status)
    }


    fun getValueString(KEY_NAME: String) : String?{
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String) : Int {
        return  sharedPreferences.getInt(KEY_NAME, 0)
    }


    fun  getValueBoolean(KEY_NAME: String, defaultValue: Boolean) : Boolean {
        return sharedPreferences.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreferences(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

}
