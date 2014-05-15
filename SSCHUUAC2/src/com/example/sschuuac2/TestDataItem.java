package com.example.sschuuac2;

public class TestDataItem {
	private double _value;
	public double getValue() {
		return _value;
	}
	public double setValue(double value) {
		_value = value;
		return value;
	}
	
	private String _label;
	
	public String getLabel() {
		return _label;
	}
	public String setLabel(String value) {
		_label = value;
		return value;
	}
}

