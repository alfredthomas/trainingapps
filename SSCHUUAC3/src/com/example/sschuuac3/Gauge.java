package com.example.sschuuac3;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.infragistics.controls.gauges.*;
import com.infragistics.graphics.SolidColorBrush;

public class Gauge {

	public static RadialGaugeView NewGauge(Context context, TestDataItem data){
	
		RadialGaugeView gauge = new RadialGaugeView(context);
		gauge.setInterval(data.getInterval());
		gauge.setValue(data.getValue());
		gauge.setMaximumValue(data.getMaxValue());
		gauge.setMinimumValue(data.getMinValue());
		
		RadialGaugeRange range1 = new RadialGaugeRange();
		range1.setStartValue(data.getStartRange());
		range1.setEndValue(data.getEndRange()) ;
		SolidColorBrush brush = new SolidColorBrush() ;
		brush.setColor(data.getColor());
		range1.setBrush(brush) ;
		range1.setOuterStartExtent(0.35);
		range1.setOuterEndExtent(0.30);
		//gauge.addRange(range1);
		gauge.setPadding(5, 5, 5, 5);
		gauge.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
		return gauge;
		
	}
	public static RadialGaugeView SetData(RadialGaugeView gauge, TestDataItem data){
		//chart already exists so new data should be applied
		gauge.setInterval(data.getInterval());
		gauge.setValue(data.getValue());
		gauge.setMaximumValue(data.getMaxValue());
		gauge.setMinimumValue(data.getMinValue());
		
		
		gauge.getRangeAt(0).setStartValue(data.getStartRange());
		gauge.getRangeAt(0).setEndValue(data.getEndRange()) ;
		((SolidColorBrush)gauge.getRangeAt(0).getBrush()).setColor(data.getColor()) ;
		//gauge.getRangeAt(0).setOuterStartExtent(0.45);
		//gauge.getRangeAt(0).setOuterEndExtent(0.40);
		return gauge;
	}
}
