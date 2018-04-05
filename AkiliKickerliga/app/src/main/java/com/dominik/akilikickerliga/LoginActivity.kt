package com.dominik.akilikickerliga

import RestService
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.dominik.akilikickerliga.model.Settings
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.dominik.akilikickerliga.model.User


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity()
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
		installAutocomplete(this)

		val btnLogin = findViewById<View>(R.id.email_sign_in_button) as Button
		btnLogin.setOnClickListener { attemptLogin() }

		mLoginFormView = findViewById(R.id.login_form)
		mProgressView = findViewById(R.id.login_progress)


	}

	private fun installAutocomplete(context: Context)
	{
		doAsync {
			val userNames = RestService.getUsers()
			uiThread {
				val suggestions = userNames.map({user -> user.name})
				val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, suggestions)
				edtUserName?.threshold = 0
				edtUserName?.setAdapter(adapter)
			}
		}

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


	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	inner class UserLoginTask internal
		constructor(private val userName: String, private val activity: Activity) : AsyncTask<Void, Void, Boolean>()
	{

		override fun doInBackground(vararg params: Void): Boolean?
		{
			// TODO: register the new account here.
			RestService.login(userName)

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

