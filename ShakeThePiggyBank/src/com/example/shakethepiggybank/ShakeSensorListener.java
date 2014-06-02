package com.example.shakethepiggybank;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ShakeSensorListener implements SensorEventListener {
	long lastUpdate = System.currentTimeMillis();
	float last_x = 0f;
	float last_y = 0f;
	float last_z = 0f;
	int pigType = 0;
	private long score = 0;
	private long pointValue = 1l;
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
		//write score to text view
        ((TextView)(((Activity)context).findViewById(com.example.shakethepiggybank.R.id.score))).setText(""+score);
        
	}

	public long getPointValue() {
		return pointValue;
	}

	public void setPointValue(long pointValue) {
		this.pointValue = pointValue;
	}

	Context context = null;
	private static final int SHAKE_THRESHOLD = 600;
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Sensor sensor = event.sensor;
		if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		    long curTime = System.currentTimeMillis();
		    // only allow one update every 200ms.
		    if ((curTime - lastUpdate) > 200) {
		      long diffTime = (curTime - lastUpdate);
		      lastUpdate = curTime;
		      float[] values = event.values;
		      float x = values[0];
		      float y = values[1];
		      float z = values[2];

		      float speed = Math.abs(x+y+z-last_x-last_y-last_z) / diffTime * 10000;
		      //if the shake is acceptable
		      if (speed > SHAKE_THRESHOLD) {
		    	  score= score+ pointValue;
		        Log.d("sensor", "shake detected w/ speed: " + speed);
		        ImageView pigShake =  ((ImageView)(((Activity)context).findViewById(com.example.shakethepiggybank.R.id.pigShake)));
		        ImageView pig =  ((ImageView)(((Activity)context).findViewById(com.example.shakethepiggybank.R.id.pig)));
		    	//show the dollar pig
		        AnimatorSet shake = new AnimatorSet();
		    	ObjectAnimator fadeOut = ObjectAnimator.ofFloat(pig, ImageView.ALPHA, 1.0f,0f);
		    	ObjectAnimator fadeIn = ObjectAnimator.ofFloat(pigShake, ImageView.ALPHA, 0f,1.0f);
		    	fadeOut.setRepeatCount(1);
		    	fadeOut.setRepeatMode(ObjectAnimator.REVERSE);
		    	fadeIn.setRepeatCount(1);
		    	fadeIn.setRepeatMode(ObjectAnimator.REVERSE);
		    	shake.play(fadeOut).with(fadeIn);
		        shake.setDuration(500);
		        shake.start();
		        
		        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		        v.vibrate(100);
		        //write score to text view
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
