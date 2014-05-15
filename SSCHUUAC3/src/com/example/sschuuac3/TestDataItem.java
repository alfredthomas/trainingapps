package com.example.sschuuac3;

import android.graphics.Color;

public class TestDataItem {
	private int value;
	private int interval;
	private int maxValue;
	private int minValue;
	private int color;
	private int startRange;
	private int endRange;
	public TestDataItem(){
		setValue((int)(Math.random()*100));
		setInterval((int)(Math.random()*100)+10);
		setMaxValue((int)(Math.random()*100)+getValue());
		setMinValue((int)(getValue()-(Math.random()*100)));
		setColor(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
		setStartRange((int)(Math.random()*8));
		setEndRange((int)(Math.random()*30));
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getStartRange() {
		return startRange;
	}
	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}
	public int getEndRange() {
		return endRange;
	}
	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}

}

