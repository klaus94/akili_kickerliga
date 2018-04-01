package com.dominik.akilikickerliga

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks

import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask

import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.dominik.akilikickerliga.model.Settings
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread

import java.util.ArrayList


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor>
{
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private var mAuthTask: UserLoginTask? = null

	// UI references.
	private var edtUserName: AutoCompleteTextView? = null
	private var mProgressView: View? = null
	private var mLoginFormView: View? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		// Set up the login form.
		edtUserName = findViewById<View>(R.id.edtName) as AutoCompleteTextView

		val btnLogin = findViewById<View>(R.id.email_sign_in_button) as Button
		btnLogin.setOnClickListener { attemptLogin() }

		mLoginFormView = findViewById(R.id.login_form)
		mProgressView = findViewById(R.id.login_progress)


	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private fun attemptLogin()
	{
		if (mAuthTask != null)
		{
			return
		}

		// Reset errors.
		edtUserName!!.error = null

		// Store values at the time of the login attempt.
		val userName = edtUserName!!.text.toString()

		var cancel = false
		var focusView: View? = null


		// Check for a valid email address.
		if (TextUtils.isEmpty(userName))
		{
			edtUserName!!.error = getString(R.string.error_field_required)
			focusView = edtUserName
			cancel = true
		}
		else if (!isUserNameValid(userName))
		{
			edtUserName!!.error = getString(R.string.error_invalid_email)
			focusView = edtUserName
			cancel = true
		}

		if (cancel)
		{
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView!!.requestFocus()
		}
		else
		{
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true)
			mAuthTask = UserLoginTask(userName, this)
			mAuthTask!!.execute(null as Void?)
		}
	}

	private fun isUserNameValid(name: String): Boolean
	{
		if (name.length < 2)
		{
			return false
		}

		return true
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private fun showProgress(show: Boolean)
	{
		val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

		mLoginFormView!!.visibility = if (show) View.GONE else View.VISIBLE
		mLoginFormView!!.animate().setDuration(shortAnimTime.toLong()).alpha((if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter()
		{
			override fun onAnimationEnd(animation: Animator)
			{
				mLoginFormView!!.visibility = if (show) View.GONE else View.VISIBLE
			}
		})

		mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
		mProgressView!!.animate().setDuration(shortAnimTime.toLong()).alpha((if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter()
		{
			override fun onAnimationEnd(animation: Animator)
			{
				mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
			}
		})
	}

	override fun onCreateLoader(i: Int, bundle: Bundle): Loader<Cursor>
	{
		return CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE),

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
	}

	override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor)
	{
		val emails = ArrayList<String>()
		cursor.moveToFirst()
		while (!cursor.isAfterLast)
		{
			emails.add(cursor.getString(ProfileQuery.ADDRESS))
			cursor.moveToNext()
		}

		addEmailsToAutoComplete(emails)
	}

	override fun onLoaderReset(cursorLoader: Loader<Cursor>)
	{

	}

	private fun addEmailsToAutoComplete(emailAddressCollection: List<String>)
	{
		doAsync {
			val userNames = RestService.getUsers()
			uiThread {
				//Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
				Log.i("test", userNames.toString())
				val adapter = ArrayAdapter(this@LoginActivity, android.R.layout.simple_dropdown_item_1line, userNames)
				edtUserName!!.setAdapter(adapter)
			}
		}

	}


	private interface ProfileQuery
	{
		companion object
		{
			val PROJECTION = arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.IS_PRIMARY)

			val ADDRESS = 0
			val IS_PRIMARY = 1
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	inner class UserLoginTask internal
		constructor(private val userName: String, private val activity: Activity) : AsyncTask<Void, Void, Boolean>()
	{

		override fun doInBackground(vararg params: Void): Boolean?
		{
			// TODO: attempt authentication against a network service.

			try
			{
				// Simulate network access.
				Thread.sleep(2000)
			}
			catch (e: InterruptedException)
			{
				return false
			}

			// TODO: register the new account here.
			RestService.createUser(userName)

			return true
		}

		override fun onPostExecute(success: Boolean?)
		{
			mAuthTask = null
			showProgress(false)

			if (success!!)
			{
				// save username
				val settings = Settings(activity)
				settings.userName = userName

				// go to main-activity
				val i = Intent(activity, MainActivity::class.java)
				startActivity(i)
				finish()
			}
		}

		override fun onCancelled()
		{
			mAuthTask = null
			showProgress(false)
		}
	}
}

