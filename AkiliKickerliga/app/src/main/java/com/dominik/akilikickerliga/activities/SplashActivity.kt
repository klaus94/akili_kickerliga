package com.dominik.akilikickerliga.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.dominik.akilikickerliga.MainActivity
import com.dominik.akilikickerliga.model.Settings

class SplashActivity : AppCompatActivity()
{
	override fun onResume()
	{
		super.onResume()

		// simulate server-communication
		try
		{
			Thread.sleep(1000)
		}
		catch (e: InterruptedException)
		{}

		val settings = Settings(this)
		val userName = settings.userName

		var i: Intent
		if (userName == "")
		{
			i = Intent(this, LoginActivity::class.java)
		}
		else
		{
			// TODO: test, if username exists
			i = Intent(this, MainActivity::class.java)
		}
		startActivity(i);
	}
}
