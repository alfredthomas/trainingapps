package com.example.sschuuac2;

import com.infragistics.controls.charts.DataChartView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

public class DataChartArrayAdapter extends BaseAdapter {
	Context context;
	int resourceID;
	TestData[] data; 
	
	
	public DataChartArrayAdapter(Context context, int resource, TestData[] objects) {
		super();
		this.context = context;
		this.resourceID = resource;
		this.data = objects;
	}
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	DataChartView chartView = null;
    	//there is no chartview object to reuse
    	if (convertView== null){
    		//external create new chart
    		chartView = DataChart.NewChart(context, data[position]);

    	}
    	//can reuse an existing object
    	else
		{
    		//set data on existing chart (size should already be applied)
    		chartView= DataChart.SetData((DataChartView) convertView, data[position]);
		}
		chartView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, position%2==0?200:400));
		return chartView;

    }
    static class ChartHolder
    {
    	View chartView;
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
		return 0;
	}
}
