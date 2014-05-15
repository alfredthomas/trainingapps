package com.example.sschuuac3;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class Rectangle {
	
	public int length;
	public int width;
	public int red;
	public int green;
	public int blue;
	
	public Rectangle()
	{
		length = (int)(Math.random()*300);
		width = (int)(Math.random()*300);
		red = (int)(Math.random()*255);
		green = (int)(Math.random()*255);
		blue = (int)(Math.random()*255);
	}
	public View Draw(Context context)
	{
		View view = new View(context);
		view.setLayoutParams(new LayoutParams(length,width));
		view.setBackgroundColor(Color.rgb(red, green, blue));;
		return view;
	}

}
