package com.example.sschuuac2;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        TestData[] data = new TestData[20];
        for(int i= 0; i<data.length; i++)
        {
        	data[i] = new TestData();
        }
        
        DataChartArrayAdapter adapter = new DataChartArrayAdapter(this, R.layout.chartitem,data);
       
        
        
        //View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        //listView1.addHeaderView(header);
        
        setListAdapter(adapter);
        if(listView1 == null)
    	{ Log.w("", "ListView is null"); }
    }
    
}
