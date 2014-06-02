package com.example.shakethepiggybank;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.ClipData.Item;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ShakeSensorListener shakeListen = new ShakeSensorListener();
	SensorManager sm = null;
	Upgrade[] upgradeArray = new Upgrade[]{
			new Upgrade(100, 2, "Two Times"),	
			new Upgrade(500, 3, "Three Times"),	
			new Upgrade(1500, 4, "Four Times"),	
			new Upgrade(5000, 5, "Five Times"),	
			new Upgrade(1000, 6, "Six Times"),	
		
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shakeListen.context = this;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        shakeListen.setScore(sharedPref.getLong(getString(R.string.Score),0l));
        shakeListen.setPointValue(sharedPref.getLong(getString(R.string.PointValue), 1l));
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListen, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        ArrayAdapter<Upgrade> adapter = new ArrayAdapter<Upgrade> (this,android.R.layout.simple_list_item_1,upgradeArray){
        	@Override
            public boolean isEnabled(int position) {
               if(upgradeArray[position].getValue()<=shakeListen.getPointValue())
            	   return false;
               return true;
              }

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				View view = super.getView(position, convertView, parent);
				if(upgradeArray[position].getValue()<shakeListen.getPointValue())
					view.setBackgroundColor(Color.RED);
				else if(upgradeArray[position].getValue()==shakeListen.getPointValue())
					view.setBackgroundColor(Color.GRAY);
				else
					view.setBackgroundColor(Color.GREEN);
				
				
				
				
				return view;
			}
        	
        };

        ListView upgrades = (ListView) findViewById(R.id.upgrade_list);
        upgrades.setAdapter(adapter);
        upgrades.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Upgrade selected = upgradeArray[position];
				if (shakeListen.getScore()>=selected.getCost())
				{
					shakeListen.setScore(shakeListen.getScore()-selected.getCost());
					shakeListen.setPointValue(selected.getValue());
					Toast.makeText(getBaseContext(),"upgrade purchased succesfully", Toast.LENGTH_SHORT).show();;
				}
				 
				
			}
		});
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(shakeListen, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        
	}

	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(shakeListen);
	}

	@Override
	protected void onDestroy() {
		
		SharedPreferences sharedPref =  getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		//long score = Long.parseLong((String) ((TextView)(findViewById(com.example.shakethepiggybank.R.id.score))).getText());
		editor.putLong(getString(R.string.PointValue), shakeListen.getPointValue());
		editor.putLong(getString(R.string.Score), shakeListen.getScore());
		editor.commit();
		
		super.onDestroy();
	}
    

}
