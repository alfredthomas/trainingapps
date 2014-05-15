package com.example.sschuuac2pt5;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestDataItem[] data = new TestDataItem[20];
        for(int i= 0; i<data.length; i++)
        {
        	data[i] = new TestDataItem();
        }
        GridView gridview = (GridView) findViewById(R.id.grid1);
        GridArrayAdapter adapter = new GridArrayAdapter(this, R.layout.chartitem,data);
       

        
         gridview.setAdapter(adapter);

    }
    
}
