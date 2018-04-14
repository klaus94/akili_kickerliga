package com.dominik.akilikickerliga.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dominik.akilikickerliga.R
import com.dominik.akilikickerliga.model.User


class HorListAdapter(private val myDataset: Array<User>) :
		RecyclerView.Adapter<MyHolder>() {

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder.
	// Each data item is just a string in this case that is shown in a TextView.
//	class ViewHolder(val myView: View) : RecyclerView.ViewHolder(myView)


	// Create new views (invoked by the layout manager)
	override fun onCreateViewHolder(parent: ViewGroup,
	                                viewType: Int): MyHolder {
		// create a new view
		val profileView = LayoutInflater.from(parent.context)
			.inflate(R.layout.profile, parent, false) as View
		// set the view's size, margins, paddings and layout parameters

		return MyHolder(profileView)
	}

	// Replace the contents of a view (invoked by the layout manager)
	override fun onBindViewHolder(holder: MyHolder, position: Int) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.txtName = myDataset[position].name
		holder.txtPoints = myDataset[position].points.toString()
//		val img = holder.myView.findViewById<View>(R.id.imageView2) as ImageView
//		val txtName = holder.myView.findViewById<View>(R.id.profile_user_name) as TextView
//		val txtPoints = holder.myView.findViewById<View>(R.id.profile_points) as TextView
//		txtName.text = myDataset[position].name
//		txtPoints.text = myDataset[position].points.toString()
	}

	// Return the size of your dataset (invoked by the layout manager)
	override fun getItemCount() = myDataset.size
}


