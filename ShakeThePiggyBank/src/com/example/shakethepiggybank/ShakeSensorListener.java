package com.example.shakethepiggybank;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeSensorListener implements SensorEventListener {
	long lastUpdate = System.currentTimeMillis();
	float last_x = 0f;
	float last_y = 0f;
	float last_z = 0f;
	int pigType = 0;
	int score = 0;
	Context context = null;
	private static final int SHAKE_THRESHOLD = 500;
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Sensor sensor = event.sensor;
		if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		    long curTime = System.currentTimeMillis();
		    // only allow one update every 100ms.
		    if ((curTime - lastUpdate) > 200) {
		      long diffTime = (curTime - lastUpdate);
		      lastUpdate = curTime;
		      float[] values = event.values;
		      float x = values[0];
		      float y = values[1];
		      float z = values[2];

		      float speed = Math.abs(x+y+z-last_x-last_y-last_z) / diffTime * 10000;

		      if (speed > SHAKE_THRESHOLD) {
		    	  score++;
		        Log.d("sensor", "shake detected w/ speed: " + speed);
		        Toast.makeText(context,score+ " shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
		        if(pigType == 0)
		        	((ImageView)(((Activity)context).findViewById(com.example.shakethepiggybank.R.id.pig))).setImageResource(com.example.shakethepiggybank.R.drawable.piggy_bank_main_shake);
		        else
		        	((ImageView)(((Activity)context).findViewById(com.example.shakethepiggybank.R.id.pig))).setImageResource(com.example.shakethepiggybank.R.drawable.piggy_bank_main);
		        pigType+=1;
		        pigType%=2; 
		        
		        ((TextView)(((Activity)context).findViewById(com.example.shakethepiggybank.R.id.score))).setText(""+score);
		        
		      }
		      last_x = x;
		      last_y = y;
		      last_z = z;
		    }
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


}
