package com.dominik.akilikickerliga.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import com.dominik.akilikickerliga.R

class MyHolder (v: View) : RecyclerView.ViewHolder(v)
{
	var txtName: String
		get() = this.toString()
		set(value) {
			txt2.text = value
		}

	var txtPoints: String
		get() = this.toString()
		set(value) {
			txt1.text = value
		}

	protected var txt1: TextView
	protected var txt2: TextView

	init
	{
		this.txt1 = v.findViewById(R.id.profile_points)
		this.txt2 = v.findViewById(R.id.profile_user_name)
	}
}