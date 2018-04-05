package com.dominik.akilikickerliga

import RestService
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.dominik.akilikickerliga.adapter.ImageAdapter
import com.dominik.akilikickerliga.model.User
import com.dominik.akilikickerliga.model.Settings
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dominik.akilikickerliga.adapter.ProfileAdapter
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
	lateinit var settings: Settings

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
		setSupportActionBar(toolbar)

		val fab = findViewById<View>(R.id.fab) as FloatingActionButton
		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
		}

		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer.addDrawerListener(toggle)
		toggle.syncState()

		val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
		navigationView.setNavigationItemSelectedListener(this)

		// init grid-view
		doAsync {

			val userList = RestService.getUsers()

			uiThread {
				val gridview = findViewById<View>(R.id.gridview) as GridView
				gridview.adapter = ProfileAdapter(this@MainActivity, userList)
				gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
					Toast.makeText(this@MainActivity, "" + position, Toast.LENGTH_SHORT).show()
				}
			}
		}

		// set username and points in side-menu
		settings = Settings(this)
		doAsync {

			val userName = settings.userName
			val user = RestService.getUser(userName)

			uiThread {
				val headerView = navigationView.getHeaderView(0)
				val txtUserName = headerView.findViewById(R.id.txtUserName) as TextView
				val txtPoints = headerView.findViewById(R.id.txtPoints) as TextView
				txtUserName.text = user?.name
				txtPoints.text = "Punkte: " + user?.points.toString()
			}
		}

	}

	override fun onBackPressed()
	{
		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START)
		}
		else
		{
			super.onBackPressed()
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		val id = item.itemId


		return if (id == R.id.action_settings)
		{
			true
		}
		else super.onOptionsItemSelected(item)

	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean
	{
		// Handle navigation view item clicks here.
		val id = item.itemId

		if (id == R.id.nav_camera)
		{
			// Handle the camera action
		}
		else if (id == R.id.nav_gallery)
		{

		}
		else if (id == R.id.nav_slideshow)
		{

		}
		else if (id == R.id.nav_manage)
		{

		}
		else if (id == R.id.nav_share)
		{

		}
		else if (id == R.id.nav_send)
		{

		}
		else if (id == R.id.nav_logout)
		{
			settings.userName = ""

			startActivity(Intent(this, LoginActivity::class.java))
		}

		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		drawer.closeDrawer(GravityCompat.START)
		return true
	}
}
