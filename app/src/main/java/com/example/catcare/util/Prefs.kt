package com.example.catcare.util

import android.content.Context
import java.util.concurrent.TimeUnit

object Prefs {
    private const val NAME = "catcare_prefs"
    private const val KEY_FEED_INTERVAL_MS = "feed_interval_ms"
    private const val KEY_NEXT_VACCINE_TIME = "next_vaccine_time"
    private const val KEY_CAT_NAME = "cat_name"
    private const val KEY_CAT_BREED = "cat_breed"
    private const val KEY_CAT_BIRTH = "cat_birth"

    fun setFeedInterval(context: Context, hours: Int, minutes: Int) {
        val ms = TimeUnit.HOURS.toMillis(hours.toLong()) + TimeUnit.MINUTES.toMillis(minutes.toLong())
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .edit().putLong(KEY_FEED_INTERVAL_MS, ms).apply()
    }

    fun getFeedIntervalMs(context: Context): Long =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getLong(KEY_FEED_INTERVAL_MS, 0L)

    fun setNextVaccineTime(context: Context, timeMillis: Long) {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .edit().putLong(KEY_NEXT_VACCINE_TIME, timeMillis).apply()
    }

    fun getNextVaccineTime(context: Context): Long =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getLong(KEY_NEXT_VACCINE_TIME, 0L)

    fun setCatInfo(context: Context, name: String, breed: String, birth: Long) {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_CAT_NAME, name)
            .putString(KEY_CAT_BREED, breed)
            .putLong(KEY_CAT_BIRTH, birth)
            .apply()
    }

    fun getCatName(context: Context): String =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_CAT_NAME, "") ?: ""

    fun getCatBreed(context: Context): String =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_CAT_BREED, "") ?: ""

    fun getCatBirth(context: Context): Long =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getLong(KEY_CAT_BIRTH, 0L)
}
