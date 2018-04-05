package com.dominik.akilikickerliga.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dominik.akilikickerliga.R
import com.dominik.akilikickerliga.model.User
import android.view.LayoutInflater



class ProfileAdapter(private val mContext: Context, private val userList: List<User>) :
		ArrayAdapter<User>(mContext, 0, userList)
{
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val profileView: View
		val user = userList[position]

		// initialize view just for the first time
		if (convertView == null)
		{
			val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
			profileView = layoutInflater.inflate(R.layout.profile, null, false)
			(profileView.findViewById<View>(R.id.profile_user_name) as TextView).text = user.name
			(profileView.findViewById<View>(R.id.profile_points) as TextView).text = user.points.toString()
		} else
		{
			profileView = convertView
		}

		return profileView
	}

}