package com.example.sschuuac2;

import android.content.Context;

import com.infragistics.controls.charts.CategoryXAxis;
import com.infragistics.controls.charts.ColumnSeries;
import com.infragistics.controls.charts.DataChartView;
import com.infragistics.controls.charts.LineSeries;
import com.infragistics.controls.charts.MarkerType;
import com.infragistics.controls.charts.NumericYAxis;
import com.infragistics.graphics.SolidColorBrush;

public class DataChart {

	public static DataChartView NewChart(Context context, TestData data){
	
		DataChartView chart = new DataChartView(context);
        
		CategoryXAxis xAxis = new CategoryXAxis();
		NumericYAxis yAxis = new NumericYAxis();
		int rand = (int)(Math.random()*Integer.MAX_VALUE);
		
		xAxis.setDataSource(data);
		xAxis.setLabel("Label");
		xAxis.setTitle("Chart");
		
		chart.addAxis(xAxis);
		chart.addAxis(yAxis);
		
		//set line series color
		SolidColorBrush brush1 = new SolidColorBrush();
		brush1.setColor(rand);
		
		//create line series
		LineSeries lineSeries = new LineSeries();
		lineSeries.setBrush(brush1);
		lineSeries.setXAxis(xAxis);
		lineSeries.setYAxis(yAxis);
		lineSeries.setValueMemberPath("Value");
		lineSeries.setDataSource(data);
		lineSeries.setMarkerType(MarkerType.AUTOMATIC);
		chart.addSeries(lineSeries);
		
		//set column series color
		SolidColorBrush brush2 = new SolidColorBrush();
		brush2.setColor(Integer.MAX_VALUE-rand);
		
		//create column series
		ColumnSeries columnSeries = new ColumnSeries();
		columnSeries.setBrush(brush2);
		columnSeries.setXAxis(xAxis);
		columnSeries.setYAxis(yAxis);
		columnSeries.setValueMemberPath("Value");
		columnSeries.setDataSource(data);
		columnSeries.setMarkerType(MarkerType.AUTOMATIC);
		
		chart.addSeries(columnSeries);
		chart.setHorizontalZoomable(true);
		chart.setVerticalZoomable(true);
		
		//add padding so the chart is not too cutoff
		chart.setPadding(5, 5, 5, 5);
		return chart;
		
	}
	public static DataChartView SetData(DataChartView chart, TestData data){
		//chart already exists so new data should be applied
		((CategoryXAxis)chart.getAxisAt(0)).setDataSource(data);
		((LineSeries)chart.getSeriesAt(0)).setDataSource(data);
		((ColumnSeries)chart.getSeriesAt(1)).setDataSource(data);
		return chart;
	}
}
