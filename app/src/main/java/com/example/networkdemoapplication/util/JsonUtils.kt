package com.example.networkdemoapplication.util


import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

val gson = Gson()

inline fun <reified T> String?.fromJson(): T? {
    this ?: return null
    return try {
        return gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> T?.toJson(): String? {
    this ?: return null
    return try {
        return gson.toJson(this)
    } catch (e: Exception) {
        null
    }
}


fun isValidJson(str: String?): Boolean {
    str ?: return false
    try {
        JSONObject(str)
    } catch (e: JSONException) {
        try {
            JSONArray(str)
        } catch (e2: JSONException) {
            return false
        }
    }
    return true
}