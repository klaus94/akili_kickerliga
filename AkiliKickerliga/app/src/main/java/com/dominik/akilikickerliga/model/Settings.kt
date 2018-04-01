package com.dominik.akilikickerliga.model

import android.content.Context
import android.content.SharedPreferences

class Settings (context: Context)
{
	val PREFS_FILENAME = "settings"
	val KEY_USER_NAME = "username"
	val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

	var userName: String
		get() = prefs.getString(KEY_USER_NAME, "")
		set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()
}