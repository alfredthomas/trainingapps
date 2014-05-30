package com.example.shakethepiggybank;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.ll);
        ShakeSensorListener shakeListen = new ShakeSensorListener();
        shakeListen.context = this;
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListen, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        ShrinkView sv = new ShrinkView(this);
        sv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
//        for(int i= 0; i<1;i++){
////        	ImageView iv = new ImageView(this);
////        	iv.setImageResource(android.R.drawable.ic_media_next);
////        	//iv.setLayoutParams(new LayoutParams(200, 200));
////        	sv.addView(iv);
//        	TextView tv = new TextView(this);
//        	
//        	tv.setText("0");
//        	sv.addView(tv);
//        }
//       rl.addView(sv);
       
       
    }
    

}
