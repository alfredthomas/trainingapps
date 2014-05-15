package com.example.sschuuac1;

import android.content.Context;

import com.infragistics.controls.charts.CategoryAxisBase;
import com.infragistics.controls.charts.CategoryXAxis;
import com.infragistics.controls.charts.ColumnSeries;
import com.infragistics.controls.charts.DataChartView;
import com.infragistics.controls.charts.LineSeries;
import com.infragistics.controls.charts.MarkerType;
import com.infragistics.controls.charts.NumericYAxis;

public class DataChart {
	DataChartView chart;
	public DataChart (Context context){
		chart = new DataChartView(context);

        TestData data = new TestData();
        
		CategoryXAxis xAxis = new CategoryXAxis();
		NumericYAxis yAxis = new NumericYAxis();
		
		xAxis.setDataSource(data);
		xAxis.setLabel("Label");
		xAxis.setTitle("done");
		
		chart.addAxis(xAxis);
		chart.addAxis(yAxis);
		
		LineSeries lineSeries = new LineSeries();
		lineSeries.setXAxis((CategoryAxisBase) chart.getAxisAt(0));
		lineSeries.setYAxis((NumericYAxis) chart.getAxisAt(1));
		lineSeries.setValueMemberPath("Value");
		lineSeries.setDataSource(data);
		lineSeries.setMarkerType(MarkerType.AUTOMATIC);
		chart.addSeries(lineSeries);
		
		ColumnSeries columnSeries = new ColumnSeries();
		columnSeries.setXAxis(xAxis);
		columnSeries.setYAxis(yAxis);
		columnSeries.setValueMemberPath("Value");
		columnSeries.setDataSource(data);
		columnSeries.setMarkerType(MarkerType.AUTOMATIC);
		
		chart.addSeries(columnSeries);
		chart.setHorizontalZoomable(true);
		chart.setVerticalZoomable(true);
	}
	public DataChartView getDataChartView()
	{
		return chart;
	}
}
