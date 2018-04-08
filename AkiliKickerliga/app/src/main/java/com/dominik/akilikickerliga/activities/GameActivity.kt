package com.dominik.akilikickerliga.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dominik.akilikickerliga.R
import android.view.View.DragShadowBuilder
import android.content.ClipData
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.DragEvent.ACTION_DRAG_ENDED
import android.widget.LinearLayout
import android.view.ViewGroup
import android.view.DragEvent.ACTION_DROP
import android.view.DragEvent.ACTION_DRAG_EXITED
import android.view.DragEvent.ACTION_DRAG_ENTERED
import android.view.DragEvent.ACTION_DRAG_STARTED
import android.R.attr.shape
import android.graphics.drawable.Drawable
import android.view.DragEvent
import android.view.View.OnDragListener
import android.R.attr.shape
import android.app.PendingIntent.getActivity
import android.content.Context
import android.support.v4.content.ContextCompat


class GameActivity : AppCompatActivity()
{
	//for reference: http://www.vogella.com/tutorials/AndroidDragAndDrop/article.html
	// todo: build table-soccer-screen, where poeple can be dragged to places of table

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_game)

		findViewById<View>(R.id.myimage1).setOnTouchListener(MyTouchListener())
		findViewById<View>(R.id.myimage2).setOnTouchListener(MyTouchListener())
		findViewById<View>(R.id.myimage3).setOnTouchListener(MyTouchListener())
		findViewById<View>(R.id.myimage4).setOnTouchListener(MyTouchListener())
		findViewById<View>(R.id.topleft).setOnDragListener(MyDragListener(this))
		findViewById<View>(R.id.topright).setOnDragListener(MyDragListener(this))
		findViewById<View>(R.id.bottomleft).setOnDragListener(MyDragListener(this))
		findViewById<View>(R.id.bottomright).setOnDragListener(MyDragListener(this))
	}
}


private class MyTouchListener : OnTouchListener
{
	override fun onTouch(view: View, motionEvent: MotionEvent): Boolean
	{
		if (motionEvent.action == MotionEvent.ACTION_DOWN)
		{
			val data = ClipData.newPlainText("", "")
			val shadowBuilder = View.DragShadowBuilder(view)
			view.startDrag(data, shadowBuilder, view, 0)
			view.visibility = View.INVISIBLE
			return true
		}
		else
		{
			return false
		}
	}
}

internal class MyDragListener(context: Context) : OnDragListener
{
	var enterShape: Drawable? = null
	var normalShape: Drawable? = null

	init
	{
		enterShape = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
		normalShape = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
	}

	override fun onDrag(v: View, event: DragEvent): Boolean
	{
		val action = event.getAction()
		when (event.getAction())
		{
			DragEvent.ACTION_DRAG_STARTED ->
			{
			}
			DragEvent.ACTION_DRAG_ENTERED -> {} //v.setBackgroundDrawable(enterShape)
			DragEvent.ACTION_DRAG_EXITED -> {} //v.setBackgroundDrawable(normalShape)
			DragEvent.ACTION_DROP ->
			{
				// Dropped, reassign View to ViewGroup
				val view = event.getLocalState() as View
				val owner = view.parent as ViewGroup
				owner.removeView(view)
				val container = v as LinearLayout
				container.addView(view)
				view.visibility = View.VISIBLE
			}
			DragEvent.ACTION_DRAG_ENDED ->
			{
				val view = event.getLocalState() as View
				view.visibility = View.VISIBLE
			} //v.setBackgroundDrawable(normalShape)
			else ->
			{
			}
		} // do nothing
		return true
	}


}