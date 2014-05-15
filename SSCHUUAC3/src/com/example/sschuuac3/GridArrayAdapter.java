package com.example.sschuuac3;

import com.infragistics.controls.gauges.RadialGaugeView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

public class GridArrayAdapter extends BaseAdapter {
	Context context;
	int resourceID;
	TestDataItem[] data; 
	
	
	public GridArrayAdapter(Context context, int resource, TestDataItem[] objects) {
		super();
		this.context = context;
		this.resourceID = resource;
		this.data = objects;
	}
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
		RadialGaugeView gaugeView = null;
    	//there is no chartview object to reuse
    	if (convertView== null){
    		//external create new chart
    		gaugeView = Gauge.NewGauge(context, data[position]);

    	}
    	//can reuse an existing object
    	else
		{
    		//set data on existing chart (size should already be applied)
    		gaugeView= Gauge.SetData((RadialGaugeView) convertView, data[position]);
		}
    	gaugeView.setLayoutParams(new AbsListView.LayoutParams(250, 250));
		return gaugeView;
		

    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data[position];
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

		

}
