package com.dominik.akilikickerliga.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.dominik.akilikickerliga.R


class ImageAdapter(private val mContext: Context) : BaseAdapter()
{

	// references to our images
	private val mThumbIds = arrayOf<Int>(
			R.drawable.sample_2,
			R.drawable.sample_3,
			R.drawable.sample_4,
			R.drawable.sample_5,
			R.drawable.sample_6,
			R.drawable.sample_7,
			R.drawable.sample_0,
			R.drawable.sample_1,
			R.drawable.sample_2,
			R.drawable.sample_3,
			R.drawable.sample_4,
			R.drawable.sample_5,
			R.drawable.sample_6,
			R.drawable.sample_7,
			R.drawable.sample_0,
			R.drawable.sample_1,
			R.drawable.sample_2,
			R.drawable.sample_3,
			R.drawable.sample_4,
			R.drawable.sample_5,
			R.drawable.sample_6,
			R.drawable.sample_7)

	override fun getCount(): Int
	{
		return mThumbIds.size
	}

	override fun getItem(position: Int): Any?
	{
		return null
	}

	override fun getItemId(position: Int): Long
	{
		return 0
	}

	// create a new ImageView for each item referenced by the Adapter
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val imageView: ImageView
		if (convertView == null)
		{
			// if it's not recycled, initialize some attributes
			imageView = ImageView(mContext)
			imageView.layoutParams = AbsListView.LayoutParams(285, 285)
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
			imageView.setPadding(8, 8, 8, 8)
		}
		else
		{
			imageView = convertView as ImageView
		}

		imageView.setImageResource(mThumbIds[position])
		return imageView
	}
}