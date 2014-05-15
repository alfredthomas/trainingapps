package com.example.sschuuac2pt5;

public class TestDataItem {
	private int value;
	private int interval;
	private int maxValue;
	private int minValue;
	private int color;
	private int startRange;
	private int endRange;
	public TestDataItem(){
		setValue((int)(Math.random()*200));
		setInterval((int)(Math.random()*50));
		setMaxValue((int)(Math.random()*200)+getValue());
		setMinValue((int)(getValue()-(Math.random()*200)));
		setColor((int)(Math.random()*Integer.MAX_VALUE));
		setStartRange((int)(Math.random()*(getValue()-getMinValue()))-getMinValue());
		setEndRange((int)(Math.random()*(getMaxValue()-getValue()))+getValue());
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

