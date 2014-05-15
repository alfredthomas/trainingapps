package com.example.sschuuac3;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.AbsListView.LayoutParams;
import android.widget.ScrollView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestDataItem[] data = new TestDataItem[50];
        WrapPanel wrapPanel = new WrapPanel(this);
        wrapPanel.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        HorizontalScrollView group1 = (HorizontalScrollView) this.findViewById(R.id.group1);
        
        for(int i= 0; i<data.length; i++)
        {
        	data[i] = new TestDataItem();
        	wrapPanel.addView(Gauge.NewGauge(this, data[i]));
        	wrapPanel.addView(new Rectangle().Draw(this));
        }
        
//        GridArrayAdapter adapter = new GridArrayAdapter(this, R.layout.chartitem,data);
//       
//
//        
//         gridview.setAdapter(adapter);
        
        group1.addView(wrapPanel);
    }
    
}
